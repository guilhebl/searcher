package com.searchprod.searcher.product.service.provider;

import com.searchprod.searcher.product.common.util.ProductDetailResponseBuilder;
import com.searchprod.searcher.product.common.util.ProductSearchResponseBuilder;
import com.searchprod.searcher.product.model.Product;
import com.searchprod.searcher.product.model.ProductDetail;
import com.searchprod.searcher.product.model.ProductSearchRequest;
import com.searchprod.searcher.product.model.ProductSearchResponse;
import com.searchprod.searcher.product.model.enumeration.ProductIdType;
import com.searchprod.searcher.product.model.walmart.WalmartSearchBaseResponse;
import com.searchprod.searcher.product.model.walmart.WalmartSearchItem;
import com.searchprod.searcher.product.model.walmart.WalmartSearchResponse;
import com.searchprod.searcher.product.model.walmart.WalmartTrendingResponse;
import com.searchprod.searcher.product.service.ImageService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.searchprod.searcher.product.common.util.LogRequestExchangeFilter.logRequest;
import static com.searchprod.searcher.product.model.enumeration.MarketplaceProvider.WALMART;
import static com.searchprod.searcher.product.model.enumeration.ProductIdType.UPC;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
public class WalmartService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WalmartService.class);

    private final ImageService imageService;
    private final String url;
    private final String path;
    private final String detailPath;
    private final String trendingPath;
    private final String responseGroup;
    private final String apiKey;
    private final String affiliateId;

    public WalmartService(
            ImageService imageService,
            @Value("${walmart.url}") String url,
            @Value("${walmart.product.path}") String path,
            @Value("${walmart.product.detail.path}") String detailPath,
            @Value("${walmart.poduct.trending.path}") String trendingPath,
            @Value("${walmart.search.response.group}") String responseGroup,
            @Value("${walmart.api.key}") String apiKey,
            @Value("${walmart.affiliate.id}") String affiliateId
    ) {
        this.imageService = imageService;
        this.url = url;
        this.path = path;
        this.detailPath = detailPath;
        this.trendingPath = trendingPath;
        this.responseGroup = responseGroup;
        this.apiKey = apiKey;
        this.affiliateId = affiliateId;
    }

    public Mono<ProductSearchResponse> searchProducts(ProductSearchRequest request) {
        LOGGER.info(String.format("search in walmart: %s", request));

        if (StringUtils.isBlank(request.getQuery())) {
            return searchTrending(request);
        }
        return searchByKeywords(request);
    }

    public Mono<ProductDetail> getProductDetail(String id, ProductIdType idType) {
        LOGGER.info(String.format("get Product detail: %s, %s", id, idType));

        try {
            boolean isSearchByUpc = idType == UPC;
            String uriPath = isSearchByUpc ? detailPath : detailPath + "/" + id;

            UriComponentsBuilder builder = UriComponentsBuilder.fromPath(uriPath)
                    .queryParam("format", "json")
                    .queryParam("apiKey", apiKey)
                    .queryParam("lsPublisherId", affiliateId);

            if (isSearchByUpc) {
                builder.queryParam(UPC.toString(), id);
            }

            var response = WebClient.builder()
                    .baseUrl(url)
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .filter(logRequest())
                    .build()
                    .get()
                    .uri(builder.encode().toUriString())
                    .accept(APPLICATION_JSON)
                    .retrieve();

            return isSearchByUpc ?
                    response.bodyToMono(WalmartSearchBaseResponse.class)
                            .flatMap(this::buildProductDetail)
                            .onErrorReturn(ProductDetailResponseBuilder.empty()) :
                    response.bodyToMono(WalmartSearchItem.class)
                            .flatMap(this::buildProductDetail)
                            .onErrorReturn(ProductDetailResponseBuilder.empty());

        } catch (Exception e) {
            LOGGER.error("Error on get product detail", e);
        }

        return Mono.empty();
    }

    private Mono<ProductSearchResponse> searchByKeywords(ProductSearchRequest request) {
        try {
            var page = request.getPage();
            var start = page > 1 ? (page - 1) * request.getPageSize() + 1 : 1;

            UriComponentsBuilder builder = UriComponentsBuilder.fromPath(path)
                    .queryParam("format", "json")
                    .queryParam("responseGroup", responseGroup)
                    .queryParam("apiKey", apiKey)
                    .queryParam("lsPublisherId", affiliateId)
                    .queryParam("start", start)
                    .queryParam("query", URLEncoder.encode(request.getQuery(), Charset.defaultCharset()));

            return WebClient.builder()
                    .baseUrl(url)
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .filter(logRequest())
                    .build()
                    .get()
                    .uri(builder.encode().toUriString())
                    .accept(APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(WalmartSearchResponse.class)
                    .flatMap(x -> buildResponse(x.getItems(), page, page, x.getTotalResults()));

        } catch (Exception e) {
            LOGGER.error("Error on search by keywords", e);
        }

        return Mono.empty();
    }

    private Mono<ProductSearchResponse> searchTrending(ProductSearchRequest request) {
        try {
            var page = request.getPage();
            UriComponentsBuilder builder = UriComponentsBuilder.fromPath(trendingPath)
                    .queryParam("format", "json")
                    .queryParam("responseGroup", responseGroup)
                    .queryParam("apiKey", apiKey)
                    .queryParam("lsPublisherId", affiliateId);

            return WebClient.builder()
                    .baseUrl(url)
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .filter(logRequest())
                    .build()
                    .get()
                    .uri(builder.encode().toUriString())
                    .accept(APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(WalmartTrendingResponse.class)
                    .flatMap(x -> buildResponse(x.getItems(), page, page, x.getItems().size()));

        } catch (Exception e) {
            LOGGER.error("Error on search trending", e);
        }
        return Mono.empty();
    }

    private Mono<ProductSearchResponse> buildResponse(
            List<WalmartSearchItem> items,
            int page,
            int pageCount,
            int totalCount) {

        if (items == null || items.isEmpty()) {
            return Mono.just(ProductSearchResponseBuilder.empty());
        }

        LOGGER.debug(String.format("build Walmart search response, num items: %d", items.size()));

        return Mono.just(ProductSearchResponseBuilder.of(
                ProductSearchResponseBuilder.ofSummary(page, pageCount, totalCount),
                items.stream().map(this::toProduct).collect(Collectors.toList())));
    }

    private Mono<ProductDetail> buildProductDetail(WalmartSearchBaseResponse item) {
        LOGGER.debug(String.format("build Walmart product detail %s", item));
        return buildProductDetail(item.getItems().get(0));
    }

    private Mono<ProductDetail> buildProductDetail(WalmartSearchItem item) {
        LOGGER.debug(String.format("build Walmart product detail %s", item));
        return (item == null || item.getName() == null) ? Mono.empty() : Mono.just(toProductDetail(item));
    }

    private ProductDetail toProductDetail(WalmartSearchItem item) {
        return new ProductDetail(
                toProduct(item),
                item.getLongDescription(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>()
        );
    }

    private Product toProduct(WalmartSearchItem item) {
        try {
            return new Product(
                    item.getItemId().toString(),
                    item.getUpc(),
                    item.getName(),
                    WALMART.name(),
                    WALMART.getUrl(),
                    item.getProductUrl(),
                    imageService.getImageUrlExternal(item.getLargeImage()),
                    imageService.getImageUrl("walmart-logo.png"),
                    BigDecimal.valueOf(Optional.ofNullable(item.getSalePrice()).orElse(0.0D)),
                    item.getCategoryPath(),
                    Optional.ofNullable(item.getNumReviews()).orElse(0),
                    Float.valueOf(Optional.ofNullable(item.getCustomerRating()).orElse("0"))
            );

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Product();
    }

}