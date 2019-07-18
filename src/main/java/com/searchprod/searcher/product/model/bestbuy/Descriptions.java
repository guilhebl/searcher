package com.searchprod.searcher.product.model.bestbuy;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Descriptions {
    @JsonProperty("short")
    private String _short;


    /**
     *
     * @return
     *     The _short
     */
    public String getShort() {
        return _short;
    }

    /**
     *
     * @param _short
     *     The short
     */
    public void setShort(String _short) {
        this._short = _short;
    }

}
