package com.searchprod.searcher.product.model.enumeration;

public enum MarketplaceProvider {
    WALMART("Walmart", "walmart.com"),
    BEST_BUY("BestBuy", "bestbuy.com"),
    EBAY("Ebay", "ebay.com");

    private final String name;
    private final String url;

    MarketplaceProvider(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return name;
    }
}
