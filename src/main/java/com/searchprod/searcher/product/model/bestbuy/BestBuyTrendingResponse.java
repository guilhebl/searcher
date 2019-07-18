package com.searchprod.searcher.product.model.bestbuy;

import java.util.List;

public class BestBuyTrendingResponse {

    private Metadata metadata;
    private List<BestBuyTrendingItem> results;

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public List<BestBuyTrendingItem> getResults() {
        return results;
    }

    public void setResults(List<BestBuyTrendingItem> results) {
        this.results = results;
    }
}

