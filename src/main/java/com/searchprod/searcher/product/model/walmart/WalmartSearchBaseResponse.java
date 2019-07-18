package com.searchprod.searcher.product.model.walmart;

import java.util.List;

public class WalmartSearchBaseResponse {
    private List<WalmartSearchItem> items;

    public List<WalmartSearchItem> getItems() {
        return items;
    }

    public void setItems(List<WalmartSearchItem> items) {
        this.items = items;
    }
}
