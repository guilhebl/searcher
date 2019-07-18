package com.searchprod.searcher.product.model;

public class ProductSearchResponseSummary {
    private Integer page;
    private Integer pageCount;
    private Integer totalCount;

    public ProductSearchResponseSummary() {
    }

    public ProductSearchResponseSummary(Integer page, Integer pageCount, Integer totalCount) {
        this.page = page;
        this.pageCount = pageCount;
        this.totalCount = totalCount;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
}
