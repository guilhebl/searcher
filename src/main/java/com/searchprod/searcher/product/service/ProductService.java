package com.searchprod.searcher.product.service;

import com.searchprod.searcher.product.common.util.MultiIterator;
import com.searchprod.searcher.product.common.util.ProductDetailResponseBuilder;
import com.searchprod.searcher.product.common.util.ProductSearchResponseBuilder;
import com.searchprod.searcher.product.model.Product;
import com.searchprod.searcher.product.model.ProductDetail;
import com.searchprod.searcher.product.model.ProductSearchRequest;
import com.searchprod.searcher.product.model.ProductSearchResponse;
import com.searchprod.searcher.product.model.enumeration.MarketplaceProvider;
import com.searchprod.searcher.product.model.enumeration.ProductIdType;
import com.searchprod.searcher.product.service.provider.BestBuyService;
import com.searchprod.searcher.product.service.provider.EbayService;
import com.searchprod.searcher.product.service.provider.WalmartService;
import io.micrometer.core.instrument.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    private final WalmartService walmartService;
    private final EbayService eBayService;
    private final BestBuyService bestBuyService;
    private final String marketplaceProviders;
    private final Long apiTimeout;

    public ProductService(
            final WalmartService walmartService,
            final EbayService eBayService,
            final BestBuyService bestBuyService,
            @Value("${api.marketplace.providers}") String marketplaceProviders,
            @Value("${api.default.timeout}") Long apiTimeout
    ) {
        this.walmartService = walmartService;
        this.eBayService = eBayService;
        this.bestBuyService = bestBuyService;
        this.marketplaceProviders = marketplaceProviders;
        this.apiTimeout = apiTimeout;
    }

    @Cacheable("productSearch")
    public Mono<ProductSearchResponse> search(ProductSearchRequest request) {
        LOGGER.debug(String.format("search: %s", request));

        var response = Mono.just(ProductSearchResponseBuilder.empty());
        return Arrays.stream(marketplaceProviders.split(","))
                .filter(StringUtils::isNotBlank)
                .map(MarketplaceProvider::valueOf)
                .map(p -> searchProvider(p, request))
                .reduce(response, (acc, x) -> acc.zipWith(x).map(y -> ProductSearchResponseBuilder.merge(y.getT1(), y.getT2())))
                .map(this::getGroupedList);
    }

    private ProductSearchResponse getGroupedList(ProductSearchResponse x) {
        List<Product> output = new LinkedList<>();
        Map<String,List<Product>> map = x.getList().stream().collect(Collectors.groupingBy(Product::getSourceName));
        var list = map.values().stream().map(List::iterator).collect(Collectors.toCollection(LinkedList::new));
        var multiIterator = new MultiIterator<>(list);

        while (multiIterator.hasNext()) {
            Product p = multiIterator.next();
            output.add(p);
        }

        return ProductSearchResponseBuilder.of(x.getSummary(), output);
    }

    /**
     * Searches for product detail in specific provider
     * @param id id to search
     * @param idType id type
     * @param source marketplace provider
     * @return product detail mono
     */
    @Cacheable("productDetail")
    public Mono<ProductDetail> getProductDetail(String id, ProductIdType idType, MarketplaceProvider source) {
        LOGGER.debug(String.format("get Product detail: %s, %s, %s", id, idType, source));

        ProductDetail productDetail = fetchProductDetail(id, idType, source).block();
        if (productDetail != null && productDetail.getProduct() != null && StringUtils.isNotBlank(productDetail.getProduct().getUpc())) {
            fetchProductDetailItems(productDetail, source);
        }
        return Mono.just(Objects.requireNonNull(productDetail));
    }

    private void fetchProductDetailItems(ProductDetail detail, MarketplaceProvider source) {
        var upc = detail.getProduct().getUpc();
        if (StringUtils.isNotBlank(upc)) {
            LOGGER.info(String.format("UPC: %s", upc));

            Arrays.stream(marketplaceProviders.split(","))
                    .filter(StringUtils::isNotBlank)
                    .map(MarketplaceProvider::valueOf)
                    .filter(provider -> provider != source)
                    .parallel()
                    .map(p -> fetchProductDetail(detail.getProduct().getUpc(), ProductIdType.UPC, p).onErrorReturn(ProductDetailResponseBuilder.empty()))
                    .forEach(x -> ProductDetailResponseBuilder.addDetailItem(detail, x.block(Duration.ofMillis(apiTimeout))));
        }
    }

    /**
     * searches in a single provider
     * @param provider source
     * @return search response
     */
    private Mono<ProductSearchResponse> searchProvider(MarketplaceProvider provider, ProductSearchRequest request) {
        switch (provider) {
            case WALMART:
                return walmartService.searchProducts(request);
            case BEST_BUY:
                return bestBuyService.searchProducts(request);
            case EBAY:
                return eBayService.searchProducts(request);
            default:
                throw new IllegalArgumentException("Error. Invalid marketplace provider");
        }
    }

    private Mono<ProductDetail> fetchProductDetail(String id, ProductIdType idType, MarketplaceProvider source) {
        switch (source) {
            case WALMART:
                return walmartService.getProductDetail(id, idType);
            case BEST_BUY:
                return bestBuyService.getProductDetail(id, idType);
            case EBAY:
                return eBayService.getProductDetail(id, idType);
            default:
                throw new IllegalArgumentException("Error. Invalid marketplace provider");
        }
    }
}
