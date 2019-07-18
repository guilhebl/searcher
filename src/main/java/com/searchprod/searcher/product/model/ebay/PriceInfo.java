package com.searchprod.searcher.product.model.ebay;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PriceInfo {

    @JsonProperty("@currencyId")
    private String currencyId;

    @JsonProperty("__value__")
    private String value;

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
