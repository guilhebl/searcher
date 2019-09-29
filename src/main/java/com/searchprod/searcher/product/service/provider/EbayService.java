package com.searchprod.searcher.product.service.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.searchprod.searcher.product.common.util.ProductDetailResponseBuilder;
import com.searchprod.searcher.product.common.util.ProductSearchResponseBuilder;
import com.searchprod.searcher.product.model.Product;
import com.searchprod.searcher.product.model.ProductDetail;
import com.searchprod.searcher.product.model.ProductSearchRequest;
import com.searchprod.searcher.product.model.ProductSearchResponse;
import com.searchprod.searcher.product.model.ebay.EbayFindingServiceResponse;
import com.searchprod.searcher.product.model.ebay.EbayProductDetailResponse;
import com.searchprod.searcher.product.model.ebay.EbaySearchItem;
import com.searchprod.searcher.product.model.ebay.EbaySearchResponse;
import com.searchprod.searcher.product.model.enumeration.ProductIdType;
import com.searchprod.searcher.product.service.ImageService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ClientCodecConfigurer;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.searchprod.searcher.product.common.util.LogRequestExchangeFilter.logRequest;
import static com.searchprod.searcher.product.model.enumeration.MarketplaceProvider.EBAY;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_PLAIN;

@Service
public class EbayService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EbayService.class);

    private final ImageService imageService;
    private final String url;
    private final String path;
    private final String defaultDataFormat;
    private final String apiKey;
    private final String apiVersion;
    private final String defaultSearchQuery;
    private final String affiliateTrackingId;
    private final String affiliateCustomId;
    private final String affiliateNetworkId;

    public EbayService(
            ImageService imageService,
            @Value("${ebay.url}") String url,
            @Value("${ebay.product.search.path}") String path,
            @Value("${ebay.default.data.format}") String defaultDataFormat,
            @Value("${ebay.product.search.default.query}") String defaultSearchQuery,
            @Value("${ebay.api.security.app.name}") String apiKey,
            @Value("${ebay.api.version}") String apiVersion,
            @Value("${ebay.affiliate.tracking.id}") String affiliateTrackingId,
            @Value("${ebay.affiliate.network.id}") String affiliateNetworkId,
            @Value("${ebay.affiliate.custom.id}") String affiliateCustomId
    ) {
        this.imageService = imageService;
        this.url = url;
        this.path = path;
        this.defaultDataFormat = defaultDataFormat;
        this.apiKey = apiKey;
        this.apiVersion = apiVersion;
        this.defaultSearchQuery = defaultSearchQuery;
        this.affiliateTrackingId = affiliateTrackingId;
        this.affiliateNetworkId = affiliateNetworkId;
        this.affiliateCustomId = affiliateCustomId;
    }

    public Mono<ProductSearchResponse> searchProducts(ProductSearchRequest request) {
        LOGGER.info(String.format("search in Ebay: %s - %s", request, defaultDataFormat));

        if (StringUtils.isBlank(request.getQuery())) {
            request.setQuery(getRandomSearchQuery());
        }

        UriComponentsBuilder builder = UriComponentsBuilder.fromPath(path)
                .queryParam("OPERATION-NAME", "findItemsByKeywords")
                .queryParam("SERVICE-VERSION", apiVersion)
                .queryParam("SECURITY-APPNAME", apiKey)
                .queryParam("GLOBAL-ID", "EBAY-US")
                .queryParam("RESPONSE-DATA-FORMAT", defaultDataFormat)
                .queryParam("affiliate.networkId", affiliateNetworkId)
                .queryParam("affiliate.trackingId", affiliateTrackingId)
                .queryParam("affiliate.customId", affiliateCustomId)
                .queryParam("outputSelector", "PictureURLLarge")
                .queryParam("paginationInput.pageNumber", request.getPage())
                .queryParam("paginationInput.entriesPerPage", request.getPageSize())
                .queryParam("keywords", URLEncoder.encode(request.getQuery(), Charset.defaultCharset()));

        return WebClient.builder()
                .baseUrl(url)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchangeStrategies(ExchangeStrategies.builder().codecs(this::acceptedCodecs).build())
                .filter(logRequest())
                .build()
                .get()
                .uri(builder.toUriString())
                .accept(APPLICATION_JSON)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .acceptCharset(Charset.defaultCharset())
                .retrieve()
                .bodyToMono(EbaySearchResponse.class)
                .flatMap(this::buildResponse);
    }

    public Mono<ProductDetail> getProductDetail(String id, ProductIdType idType) {
        LOGGER.info(String.format("get Product detail: %s, %s", id, idType));
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath(path)
                .queryParam("OPERATION-NAME", "findItemsByKeywords")
                .queryParam("SERVICE-VERSION", apiVersion)
                .queryParam("SECURITY-APPNAME", apiKey)
                .queryParam("GLOBAL-ID", "EBAY-US")
                .queryParam("RESPONSE-DATA-FORMAT", defaultDataFormat)
                .queryParam("affiliate.networkId", affiliateNetworkId)
                .queryParam("affiliate.trackingId", affiliateTrackingId)
                .queryParam("affiliate.customId", affiliateCustomId)
                .queryParam("outputSelector", "PictureURLLarge")
                .queryParam("paginationInput.pageNumber", 1)
                .queryParam("paginationInput.entriesPerPage", 1)
                .queryParam("keywords", id);

        return WebClient.builder()
                .baseUrl(url)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchangeStrategies(ExchangeStrategies.builder().codecs(this::acceptedCodecs).build())
                .filter(logRequest())
                .build()
                .get()
                .uri(builder.toUriString())
                .accept(APPLICATION_JSON)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .acceptCharset(Charset.defaultCharset())
                .retrieve()
                .bodyToMono(EbaySearchResponse.class)
                .flatMap(this::buildResponseProductDetail);
    }

    private void acceptedCodecs(ClientCodecConfigurer clientCodecConfigurer) {
        clientCodecConfigurer.customCodecs().encoder(new Jackson2JsonEncoder(new ObjectMapper(), TEXT_PLAIN));
        clientCodecConfigurer.customCodecs().decoder(new Jackson2JsonDecoder(new ObjectMapper(), TEXT_PLAIN));
    }

    private String getIdTypeEbay(ProductIdType idType) {
        switch (idType) {
            case ID:
                return "ReferenceID";
            case UPC:
                return "UPC";
            default:
                throw new IllegalArgumentException(String.format("Invalid Ebay Id Type %s", idType));
        }
    }

    private Mono<ProductDetail> buildResponse(EbayProductDetailResponse response) {
        LOGGER.debug(String.format("build Ebay product detail %s", response));

        if (!isValidProductDetailResponse(response)) {
            return Mono.just(ProductDetailResponseBuilder.empty());
        }

        var item = response.getFindItemsByProductResponse().get(0).getSearchResult().get(0).getItem().get(0);
        return Mono.just(ProductDetailResponseBuilder.of(toProduct(item)));
    }

    private boolean isValidEbayFindingServiceResponse(EbayFindingServiceResponse item) {
        if (item.getErrorMessage() != null && !item.getErrorMessage().isEmpty()) {
            item.getErrorMessage().get(0).getError().forEach(x -> LOGGER.error(x.toString()));
            return false;
        }

        return item.getSearchResult() != null
                && !item.getSearchResult().isEmpty()
                && item.getSearchResult().get(0).getItem() != null
                && !item.getSearchResult().get(0).getItem().isEmpty();
    }

    private boolean isValidProductDetailResponse(EbayProductDetailResponse response) {
        EbayFindingServiceResponse item = response.getFindItemsByProductResponse().get(0);
        return isValidEbayFindingServiceResponse(item);
    }

    private Mono<ProductDetail> buildResponseProductDetail(EbaySearchResponse response) {
        LOGGER.debug(String.format("build Ebay product detail %s", response));

        var searchResponse = response.getFindItemsByKeywordsResponse().get(0);
        if (!isValidEbayFindingServiceResponse(searchResponse)) {
            return Mono.just(ProductDetailResponseBuilder.empty());
        }

        return Mono.just(ProductDetailResponseBuilder.of(
                toProduct(searchResponse.getSearchResult().get(0).getItem().get(0)))
        );
    }

    /**
     * Checks for error if no errors build and returns message otherwise returns empty
     * @param response response from ebay finding api
     * @return empty or response
     */
    private Mono<ProductSearchResponse> buildResponse(EbaySearchResponse response) {
         LOGGER.debug(String.format("build eBay search response, num items: %s", response));

        var searchResponse = response.getFindItemsByKeywordsResponse().get(0);
        if (!isValidEbayFindingServiceResponse(searchResponse)) {
            return Mono.just(ProductSearchResponseBuilder.empty());
        }

        var items = searchResponse.getSearchResult().get(0).getItem();
        return Mono.just(ProductSearchResponseBuilder.of(
                ProductSearchResponseBuilder.ofSummary(
                        Integer.valueOf(searchResponse.getPaginationOutput().get(0).getPageNumber().get(0)),
                        Integer.valueOf(searchResponse.getPaginationOutput().get(0).getTotalPages().get(0)),
                        Integer.valueOf(searchResponse.getPaginationOutput().get(0).getTotalEntries().get(0))),
                items.stream().map(this::toProduct).collect(Collectors.toList())));
    }

    private Product toProduct(EbaySearchItem item) {
        return new Product(
                Optional.ofNullable(item.getItemId().get(0)).orElse(""),
                "",
                StringUtils.join(item.getTitle()),
                EBAY.name(),
                EBAY.getUrl(),
                Optional.ofNullable(item.getViewItemURL().get(0)).orElse(""),
                imageService.getImageUrlExternal(item.getPictureURLLarge() != null ? item.getPictureURLLarge().get(0) : ""),
                imageService.getImageUrl("ebay-logo.png"),
                BigDecimal.valueOf(Double.parseDouble(Optional.ofNullable(item.getSellingStatus().get(0).getConvertedCurrentPrice().get(0).getValue()).orElse("0"))),
                StringUtils.join(item.getPrimaryCategory().get(0).getCategoryName()),
                0,
                0.0f
        );
    }

    private String getRandomSearchQuery() {
        var queries = defaultSearchQuery.split(",");
        var idx = ThreadLocalRandom.current().nextInt(queries.length);
        return queries[idx];
    }

}