package com.searchprod.searcher.product.model;

import java.util.Objects;

public class ProductSearchRequest {
    private String query;
    private String sortBy;
    private String sortOrder;
    private Integer page;
    private Integer pageSize;

    public ProductSearchRequest() {
    }

    public ProductSearchRequest(String query, String sortBy, String sortOrder, Integer page, Integer pageSize) {
        this.query = query;
        this.sortBy = sortBy;
        this.sortOrder = sortOrder;
        this.page = page;
        this.pageSize = pageSize;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductSearchRequest that = (ProductSearchRequest) o;
        return Objects.equals(query, that.query) &&
                Objects.equals(sortBy, that.sortBy) &&
                Objects.equals(sortOrder, that.sortOrder) &&
                Objects.equals(page, that.page) &&
                Objects.equals(pageSize, that.pageSize);
    }

    @Override
    public int hashCode() {
        return Objects.hash(query, sortBy, sortOrder, page, pageSize);
    }

    @Override
    public String toString() {
        return "ProductSearchRequest{" +
                "query='" + query + '\'' +
                ", sortBy='" + sortBy + '\'' +
                ", sortOrder='" + sortOrder + '\'' +
                ", page=" + page +
                ", pageSize=" + pageSize +
                '}';
    }
}
