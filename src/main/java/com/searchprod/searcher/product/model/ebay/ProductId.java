package com.searchprod.searcher.product.model.ebay;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductId {
    @JsonProperty("@type")
    private String type;

    @JsonProperty("__value__")
    private String value;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
