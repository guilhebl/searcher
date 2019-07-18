package com.searchprod.searcher.product.model;

import java.util.List;
import java.util.Objects;

public class ProductSearchResponse {
    private ProductSearchResponseSummary summary;
    private List<Product> list;

    public ProductSearchResponse() {
    }

    public ProductSearchResponse(ProductSearchResponseSummary summary, List<Product> list) {
        this.summary = summary;
        this.list = list;
    }

    public ProductSearchResponseSummary getSummary() {
        return summary;
    }

    public void setSummary(ProductSearchResponseSummary summary) {
        this.summary = summary;
    }

    public List<Product> getList() {
        return list;
    }

    public void setList(List<Product> list) {
        this.list = list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductSearchResponse that = (ProductSearchResponse) o;
        return Objects.equals(summary, that.summary) &&
                Objects.equals(list, that.list);
    }

    @Override
    public int hashCode() {
        return Objects.hash(summary, list);
    }
}
