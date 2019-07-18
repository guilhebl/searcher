package com.searchprod.searcher.product.model.ebay;

import java.util.List;

public class PaginationOutput {
    private List<String> pageNumber;
    private List<String> entriesPerPage;
    private List<String> totalPages;
    private List<String> totalEntries;

    public List<String> getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(List<String> pageNumber) {
        this.pageNumber = pageNumber;
    }

    public List<String> getEntriesPerPage() {
        return entriesPerPage;
    }

    public void setEntriesPerPage(List<String> entriesPerPage) {
        this.entriesPerPage = entriesPerPage;
    }

    public List<String> getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(List<String> totalPages) {
        this.totalPages = totalPages;
    }

    public List<String> getTotalEntries() {
        return totalEntries;
    }

    public void setTotalEntries(List<String> totalEntries) {
        this.totalEntries = totalEntries;
    }
}
