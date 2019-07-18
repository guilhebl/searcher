package com.searchprod.searcher.product.model.ebay;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

//@JsonIgnoreProperties(ignoreUnknown=true)
public class EbaySearchItems {

    @JsonProperty("@count")
    private String count;

    private List<EbaySearchItem> item;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<EbaySearchItem> getItem() {
        return item;
    }

    public void setItem(List<EbaySearchItem> item) {
        this.item = item;
    }
}
