package com.searchprod.searcher.product.controller;

import com.searchprod.searcher.product.common.util.ProductSearchRequestBuilder;
import com.searchprod.searcher.product.model.ProductDetail;
import com.searchprod.searcher.product.model.ProductSearchResponse;
import com.searchprod.searcher.product.model.enumeration.MarketplaceProvider;
import com.searchprod.searcher.product.model.enumeration.ProductIdType;
import com.searchprod.searcher.product.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/search")
    public Mono<ProductSearchResponse> search(@RequestParam("q") Optional<String> query) {
        return productService.search(query.isPresent() ?
                ProductSearchRequestBuilder.of(query.get()) :
                ProductSearchRequestBuilder.empty());
    }

    @GetMapping("/detail/{id}")
    public Mono<ProductDetail> getProductDetail(@PathVariable("id") String id, @RequestParam("idType") ProductIdType idType, @RequestParam("source") MarketplaceProvider source) {
        return productService.getProductDetail(id, idType, source);
    }

}