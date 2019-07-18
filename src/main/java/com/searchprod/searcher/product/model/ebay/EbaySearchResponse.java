package com.searchprod.searcher.product.model.ebay;

import java.util.List;

public class EbaySearchResponse {
    private List<EbayFindingServiceResponse> findItemsByKeywordsResponse;

    public List<EbayFindingServiceResponse> getFindItemsByKeywordsResponse() {
        return findItemsByKeywordsResponse;
    }

    public void setFindItemsByKeywordsResponse(List<EbayFindingServiceResponse> findItemsByKeywordsResponse) {
        this.findItemsByKeywordsResponse = findItemsByKeywordsResponse;
    }
}
