package com.searchprod.searcher.product.model.ebay;

import java.util.List;

public class EbayProductDetailResponse {
    private List<EbayFindingServiceResponse> findItemsByProductResponse;

    public List<EbayFindingServiceResponse> getFindItemsByProductResponse() {
        return findItemsByProductResponse;
    }

    public void setFindItemsByProductResponse(List<EbayFindingServiceResponse> findItemsByProductResponse) {
        this.findItemsByProductResponse = findItemsByProductResponse;
    }
}
