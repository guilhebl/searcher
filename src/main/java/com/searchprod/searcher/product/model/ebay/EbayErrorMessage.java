package com.searchprod.searcher.product.model.ebay;

import java.util.List;

public class EbayErrorMessage {
    private List<EbayError> error;

    public List<EbayError> getError() {
        return error;
    }

    public void setError(List<EbayError> error) {
        this.error = error;
    }
}
