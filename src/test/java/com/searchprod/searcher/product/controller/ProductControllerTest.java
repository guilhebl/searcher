package com.searchprod.searcher.product.controller;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@TestPropertySource(locations="classpath:test.properties")
public class ProductControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    private WireMockServer wireMockServer;

    @Value("${wiremock.port}")
    private Integer port;

    @BeforeEach
    public void setup () {
        wireMockServer = new WireMockServer(port);
        wireMockServer.start();
        setupStub();
    }

    @AfterEach
    public void teardown () {
        wireMockServer.stop();
    }

    public void setupStub() {
        wireMockServer.stubFor(
                get(urlPathEqualTo("/v1/trends"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBodyFile("json/walmart/search_trending.json")));

        wireMockServer.stubFor(
                get(urlPathEqualTo("/v1/search"))
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .withBodyFile("json/walmart/product_search.json")));

        wireMockServer.stubFor(
                get(urlPathEqualTo("/v1/search"))
                        .withQueryParam("query", equalTo("testtesttesttesttesttesttesttest"))
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .withBodyFile("json/walmart/product_search_not_found.json")));

        wireMockServer.stubFor(
                get(urlPathEqualTo("/v1/items"))
                        .withQueryParam("upc", equalTo("627988401003"))
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .withBodyFile("json/walmart/product_detail.json")));

        wireMockServer.stubFor(
                get(urlPathEqualTo("/v1/items"))
                        .withQueryParam("format", equalTo("json"))
                        .withQueryParam("apiKey", equalTo("test"))
                        .withQueryParam("upc", equalTo("1"))
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .withBodyFile("json/walmart/product_detail_not_found.json")));

        wireMockServer.stubFor(
                get(urlPathEqualTo("/beta/products/trendingViewed"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBodyFile("json/bestbuy/search_trending.json")));

        wireMockServer.stubFor(
                get(urlPathEqualTo("/v1/products(search=test)"))
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .withBodyFile("json/bestbuy/product_search.json")));

        wireMockServer.stubFor(
                get(urlPathEqualTo("/v1/products(search=testtesttesttesttesttesttesttest)"))
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .withBodyFile("json/bestbuy/product_search_not_found.json")));

        wireMockServer.stubFor(
                get(urlPathEqualTo("/v1/products(search=state&search=of&search=the&search=art)"))
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .withBodyFile("json/bestbuy/product_search_many.json")));


        wireMockServer.stubFor(
                get(urlPathEqualTo("/v1/products(search=state&search=%252521%252540)"))
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .withBodyFile("json/bestbuy/product_search_not_found.json")));

        wireMockServer.stubFor(
                get(urlPathEqualTo("/v1/products(upc=627988401003)"))
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .withBodyFile("json/bestbuy/product_detail.json")));

        wireMockServer.stubFor(
                get(urlPathEqualTo("/v1/products(upc=1)"))
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .withBodyFile("json/bestbuy/product_detail_not_found.json")));

        wireMockServer.stubFor(
                get(urlPathEqualTo("/services/search/FindingService/v1"))
                        .withQueryParam("OPERATION-NAME", equalTo("findItemsByKeywords"))
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withHeader(HttpHeaders.CONTENT_TYPE, "text/plain; charset=UTF-8")
                                .withBodyFile("json/ebay/product_search.json")));

        wireMockServer.stubFor(
                get(urlPathEqualTo("/services/search/FindingService/v1"))
                        .withQueryParam("OPERATION-NAME", equalTo("findItemsByKeywords"))
                        .withQueryParam("keywords", equalTo("testtesttesttesttesttesttesttest"))
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withHeader(HttpHeaders.CONTENT_TYPE, "text/plain; charset=UTF-8")
                                .withBodyFile("json/ebay/product_search_not_found.json")));

        wireMockServer.stubFor(
                get(urlPathEqualTo("/services/search/FindingService/v1"))
                        .withQueryParam("OPERATION-NAME", equalTo("findItemsByProduct"))
                        .withQueryParam("productId", equalTo("627988401003"))
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withHeader(HttpHeaders.CONTENT_TYPE, "text/plain; charset=UTF-8")
                                .withBodyFile("json/ebay/product_detail.json")));

        wireMockServer.stubFor(
                get(urlPathEqualTo("/services/search/FindingService/v1"))
                        .withQueryParam("OPERATION-NAME", equalTo("findItemsByProduct"))
                        .withQueryParam("productId", equalTo("1"))
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withHeader(HttpHeaders.CONTENT_TYPE, "text/plain; charset=UTF-8")
                                .withBodyFile("json/ebay/product_detail_not_found.json")));

    }

    @Test
    public void testSearch() {
        testSearchByQuery("");
    }

    @Test
    public void testSearchByKeyword() {
        testSearchByQuery("test");
    }

    @Test
    public void testSearchByKeywordPhrase() {
        testSearchByQuery("state of the art");
    }

    @Test
    public void testSearchByKeywordPhraseCacheHit() {
        for(int i = 0; i < 10; i++) {
            testSearchByQuery("state of the art");
        }
    }

    @Test
    public void testSearchByKeywordPhraseSpecialChars() {
        testSearchByQuery("state !@#$%^^&*()_+=-");
    }

    @Test
    public void testSearchByKeyword_notFound() {
        testSearchByQuery("testtesttesttesttesttesttesttest", true);
    }

    @Test
    public void testGetProductDetail() {
        webTestClient.get().uri("/products/detail/627988401003?idType=UPC&source=BEST_BUY")
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .expectBody()
                .jsonPath("$.product").isNotEmpty()
                .jsonPath("$.productDetailItems").isNotEmpty()
                .jsonPath("$.product.id").isEqualTo("627988401003");
    }

    @Test
    public void testGetProductDetailInvalidParam_idType() {
        webTestClient.get().uri("/products/detail/627988401003?idType=XZY&source=BEST_BUY")
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody();
    }

    @Test
    public void testGetProductDetailInvalidParam_source() {
        webTestClient.get().uri("/products/detail/627988401003?idType=UPC&source=XZY")
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody();
    }

    @Test
    public void testGetProductDetail_notFound() {
        webTestClient.get().uri("/products/detail/1?idType=UPC&source=WALMART")
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.product").isEmpty()
                .jsonPath("$.attributes").isEmpty()
                .jsonPath("$.productStats").isEmpty()
                .jsonPath("$.description").isEmpty()
                .jsonPath("$.productDetailItems").isEmpty();
    }

    private void testSearchByQuery(String q, boolean isEmpty) {
        var sb = new StringBuilder("/products/search");
        if (!q.equals("")) {
            sb.append("?q=").append(q);
        }

        var bodySpec = webTestClient.get().uri(sb.toString())
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .expectBody();

        if (isEmpty) {
            bodySpec.jsonPath("$.list").isEmpty()
                    .jsonPath("$.summary.page").isEqualTo(1)
                    .jsonPath("$.summary.totalCount").isEqualTo(0);
        } else {
            bodySpec.jsonPath("$.list").isNotEmpty()
                    .jsonPath("$.summary.page").isEqualTo(1);
        }
    }

    private void testSearchByQuery(String q) {
        testSearchByQuery(q,false);
    }

}
