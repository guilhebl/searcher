package com.searchprod.searcher.product.model.ebay;

import java.util.List;

public class EbayFindingServiceResponse {
    private List<String> ack;
    private List<String> version;
    private List<String> timestamp;
    private List<String> itemSearchURL;
    private List<EbaySearchItems> searchResult;
    private List<PaginationOutput> paginationOutput;
    private List<EbayErrorMessage> errorMessage;

    public List<String> getAck() {
        return ack;
    }

    public void setAck(List<String> ack) {
        this.ack = ack;
    }

    public List<String> getVersion() {
        return version;
    }

    public void setVersion(List<String> version) {
        this.version = version;
    }

    public List<String> getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(List<String> timestamp) {
        this.timestamp = timestamp;
    }

    public List<String> getItemSearchURL() {
        return itemSearchURL;
    }

    public void setItemSearchURL(List<String> itemSearchURL) {
        this.itemSearchURL = itemSearchURL;
    }

    public List<EbaySearchItems> getSearchResult() {
        return searchResult;
    }

    public void setSearchResult(List<EbaySearchItems> searchResult) {
        this.searchResult = searchResult;
    }

    public List<PaginationOutput> getPaginationOutput() {
        return paginationOutput;
    }

    public void setPaginationOutput(List<PaginationOutput> paginationOutput) {
        this.paginationOutput = paginationOutput;
    }

    public List<EbayErrorMessage> getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(List<EbayErrorMessage> errorMessage) {
        this.errorMessage = errorMessage;
    }
}
