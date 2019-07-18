package com.searchprod.searcher.product.service;

import com.searchprod.searcher.product.common.util.ProductDetailResponseBuilder;
import com.searchprod.searcher.product.common.util.ProductSearchResponseBuilder;
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

import java.util.Arrays;

@Service
public class ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    private final WalmartService walmartService;
    private final EbayService eBayService;
    private final BestBuyService bestBuyService;
    private final String marketplaceProviders;

    public ProductService(
            final WalmartService walmartService,
            final EbayService eBayService,
            final BestBuyService bestBuyService,
            @Value("${api.marketplace.providers}") String marketplaceProviders
    ) {
        this.walmartService = walmartService;
        this.eBayService = eBayService;
        this.bestBuyService = bestBuyService;
        this.marketplaceProviders = marketplaceProviders;
    }

    /**
     * Searches for products in all providers
     * @param request search request
     * @return search list response
     */
    @Cacheable("productSearch")
    public Mono<ProductSearchResponse> search(ProductSearchRequest request) {
        LOGGER.debug(String.format("search: %s", request));

        try {
            var response = Mono.just(ProductSearchResponseBuilder.empty());
            return Arrays.stream(marketplaceProviders.split(","))
                    .filter(StringUtils::isNotBlank)
                    .map(MarketplaceProvider::valueOf)
                    .map(p -> searchProvider(p, request))
                    .reduce(response, (acc, x) -> acc.zipWith(x).map(y -> ProductSearchResponseBuilder.merge(y.getT1(), y.getT2())));

        } catch(Exception e) {
            LOGGER.error("error while searching", e);
        }
        return Mono.empty();
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

        try {
            Mono<ProductDetail> productDetailMono = fetchProductDetail(id, idType, source);

            return Arrays.stream(marketplaceProviders.split(","))
                    .filter(StringUtils::isNotBlank)
                    .map(MarketplaceProvider::valueOf)
                    .filter(x -> x != source)
                    .map(p -> fetchProductDetail(id, idType, p))
                    .reduce(productDetailMono, (acc, x) -> acc.zipWith(x).map(y -> ProductDetailResponseBuilder.addDetailItem(y.getT1(), y.getT2())));

        } catch(Exception e) {
            LOGGER.error("error on get detail", e);
        }
        return Mono.empty();
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
