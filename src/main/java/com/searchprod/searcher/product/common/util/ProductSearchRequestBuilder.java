package com.searchprod.searcher.product.common.util;

import com.searchprod.searcher.product.model.ProductSearchRequest;

public class ProductSearchRequestBuilder {

    private static final ProductSearchRequest EMPTY = getInstance().build();
    private static ProductSearchRequestBuilder instance;

    private String query;
    private String sortBy;
    private String sortOrder;
    private Integer page;
    private Integer pageSize;

    private ProductSearchRequestBuilder() {
        reset();
    }

    private void reset() {
        query = "";
        sortBy = "";
        sortOrder = "";
        page = 1;
        pageSize = 10;
    }

    public static ProductSearchRequestBuilder getInstance() {
        if (instance == null) {
            instance = new ProductSearchRequestBuilder();
        }
        return instance;
    }

    public ProductSearchRequestBuilder setQuery(String query) {
        this.query = query;
        return this;
    }

    public ProductSearchRequestBuilder setPage(Integer page) {
        this.page = page;
        return this;
    }

    public ProductSearchRequestBuilder setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public static ProductSearchRequest empty() {
        return EMPTY;
    }

    public static ProductSearchRequest of(String query) {
        return getInstance().setQuery(query).build();
    }

    public ProductSearchRequest build() {
        final ProductSearchRequest request = new ProductSearchRequest(
                this.query,
                this.sortBy,
                this.sortOrder,
                this.page,
                this.pageSize
        );
        reset();
        return request;
    }
}
