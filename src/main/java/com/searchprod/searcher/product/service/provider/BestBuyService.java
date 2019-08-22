package com.searchprod.searcher.product.service.provider;

import com.searchprod.searcher.product.common.util.ProductDetailResponseBuilder;
import com.searchprod.searcher.product.common.util.ProductSearchResponseBuilder;
import com.searchprod.searcher.product.model.Product;
import com.searchprod.searcher.product.model.ProductDetail;
import com.searchprod.searcher.product.model.ProductSearchRequest;
import com.searchprod.searcher.product.model.ProductSearchResponse;
import com.searchprod.searcher.product.model.bestbuy.BestBuySearchItem;
import com.searchprod.searcher.product.model.bestbuy.BestBuySearchResponse;
import com.searchprod.searcher.product.model.bestbuy.BestBuyTrendingItem;
import com.searchprod.searcher.product.model.bestbuy.BestBuyTrendingResponse;
import com.searchprod.searcher.product.model.bestbuy.CategoryPath;
import com.searchprod.searcher.product.model.enumeration.ProductIdType;
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

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.searchprod.searcher.product.common.util.LogRequestExchangeFilter.logRequest;
import static com.searchprod.searcher.product.model.enumeration.MarketplaceProvider.BEST_BUY;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
public class BestBuyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BestBuyService.class);

    private final ImageService imageService;
    private final String url;
    private final String path;
    private final String trendingPath;
    private final String fieldList;
    private final String apiKey;
    private final String linkShareId;

    public BestBuyService(
            ImageService imageService,
            @Value("${bestbuy.url}") String url,
            @Value("${bestbuy.product.path}") String path,
            @Value("${bestbuy.product.trending.path}") String trendingPath,
            @Value("${bestbuy.field.list}") String fieldList,
            @Value("${bestbuy.api.key}") String apiKey,
            @Value("${bestbuy.linkshare.id}") String linkShareId
    ) {
        this.imageService = imageService;
        this.url = url;
        this.path = path;
        this.trendingPath = trendingPath;
        this.fieldList = fieldList;
        this.apiKey = apiKey;
        this.linkShareId = linkShareId;
    }

    public Mono<ProductSearchResponse> searchProducts(ProductSearchRequest request) {
        LOGGER.info(String.format("search in Best buy: %s", request));

        if (StringUtils.isBlank(request.getQuery())) {
            return searchTrending();
        }
        return searchByKeywords(request);
    }

    public Mono<ProductDetail> getProductDetail(String id, ProductIdType idType) {
        LOGGER.info(String.format("get Product detail: %s, %s", id, idType));

        try {
            String uriPath = String.format("%s(%s=%s)", path, getIdTypeBestBuy(idType), id);
            UriComponentsBuilder builder = UriComponentsBuilder.fromPath(uriPath)
                    .queryParam("format", "json")
                    .queryParam("apiKey", apiKey)
                    .queryParam("LID", linkShareId);

            return WebClient.builder()
                    .baseUrl(url)
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .filter(logRequest())
                    .build()
                    .get()
                    .uri(builder.encode().toUriString())
                    .accept(APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(BestBuySearchResponse.class)
                    .flatMap(this::buildProductDetail);

        } catch (Exception e) {
            LOGGER.error("Error on get product detail", e);
        }

        return Mono.empty();
    }

    private String getIdTypeBestBuy(ProductIdType idType) {
        switch (idType) {
            case ID: return "sku";
            case UPC: return "upc";
            default: throw new IllegalArgumentException(String.format("Invalid Id Type %s", idType));
        }
    }

    private Mono<ProductDetail> buildProductDetail(BestBuySearchResponse response) {
        if (response.getProducts().isEmpty()) {
            return Mono.just(ProductDetailResponseBuilder.empty());
        }

        var item = response.getProducts().get(0);
        return Mono.just(ProductDetailResponseBuilder.of(toProduct(item)));
    }

    private Mono<ProductSearchResponse> searchTrending() {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromPath(trendingPath)
                    .queryParam("format", "json")
                    .queryParam("apiKey", apiKey)
                    .queryParam("LID", linkShareId);

            return WebClient.builder()
                    .baseUrl(url)
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .filter(logRequest())
                    .build()
                    .get()
                    .uri(builder.encode().toUriString())
                    .accept(APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(BestBuyTrendingResponse.class)
                    .flatMap(x -> buildResponse(x));

        } catch (Exception e) {
            LOGGER.error("Error on search by keywords", e);
        }

        return Mono.empty();
    }

    private Mono<ProductSearchResponse> buildResponse(BestBuyTrendingResponse response) {
        LOGGER.debug(String.format("build bestbuy trending response, num items: %d", response.getResults().size()));

        return Mono.just(ProductSearchResponseBuilder.of(
                ProductSearchResponseBuilder.ofSummary(1, 1, response.getResults().size()),
                response.getResults().stream().map(this::toProduct).collect(Collectors.toList())));
    }

    private Mono<ProductSearchResponse> searchByKeywords(ProductSearchRequest request) {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromPath(String.format("%s%s",  path, buildKeywordSearchPath(request.getQuery())))
                    .queryParam("format", "json")
                    .queryParam("apiKey", apiKey)
                    .queryParam("LID", linkShareId)
                    .queryParam("show", fieldList)
                    .queryParam("page", request.getPage())
                    .queryParam("pageSize", request.getPageSize());

            return WebClient.builder()
                    .baseUrl(url)
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .filter(logRequest())
                    .build()
                    .get()
                    .uri(builder.encode().toUriString())
                    .accept(APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(BestBuySearchResponse.class)
                    .flatMap(this::buildResponse);

        } catch (Exception e) {
            LOGGER.error("Error on search by keywords", e);
        }

        return Mono.empty();
    }

    /**
     * Builds search path pattern for US best buy api
     * sample: input 'deals of the day' : output -> (search=deals&search=of&search=the&search=day)
     * @param query input query
     * @return formatted path
     */
    private String buildKeywordSearchPath(String query) {
        var res = Arrays.stream(query.split(" ")).map(x -> "search=" + URLEncoder.encode(x, Charset.defaultCharset())).collect(Collectors.joining("&"));
        return "(" + res + ")";
    }

    private Mono<ProductSearchResponse> buildResponse(BestBuySearchResponse response) {
        LOGGER.debug(String.format("build bestbuy search response, num items: %d", response.getProducts().size()));

        return Mono.just(ProductSearchResponseBuilder.of(
                ProductSearchResponseBuilder.ofSummary(response.getCurrentPage(), response.getTotalPages(), response.getTotal()),
                response.getProducts().stream().map(this::toProduct).collect(Collectors.toList())));
    }

    private Product toProduct(BestBuyTrendingItem item) {
        return new Product(
                item.getSku(),
                "",
                item.getNames().getTitle(),
                BEST_BUY.name(),
                BEST_BUY.getUrl(),
                item.getLinks().getWeb(),
                imageService.getImageUrlExternal(item.getImages().getStandard()),
                imageService.getImageUrl("best-buy-logo.png"),
                item.getPrices().getCurrent(),
                "trending",
                Optional.ofNullable(item.getCustomerReviews().getCount()).orElse(0),
                Float.valueOf(Optional.ofNullable(item.getCustomerReviews().getAverageScore()).orElse("0"))
        );
    }

    private Product toProduct(BestBuySearchItem item) {
        return new Product(
                getProductItemId(item),
                item.getUpc(),
                item.getName(),
                BEST_BUY.name(),
                BEST_BUY.getUrl(),
                item.getUrl(),
                imageService.getImageUrlExternal(item.getImage()),
                imageService.getImageUrl("best-buy-logo.png"),
                item.getSalePrice(),
                buildCategoryPath(item.getCategoryPath()),
                Optional.ofNullable(item.getCustomerReviewCount()).orElse(0),
                Float.valueOf(Optional.ofNullable(item.getCustomerReviewAverage()).orElse("0"))
        );
    }

    private String buildCategoryPath(List<CategoryPath> categoryPath) {
        var list = categoryPath.stream().map(x -> x.getName()).collect(Collectors.toList());
        return StringUtils.join(list, "-");
    }

    private String getProductItemId(BestBuySearchItem item) {
        return Optional.ofNullable(item.getUpc()).orElse("");
    }

}