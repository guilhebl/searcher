package com.searchprod.searcher.product.model.enumeration;

public enum MarketplaceProvider {
    WALMART("walmart.com"),
    BEST_BUY("bestbuy.com"),
    EBAY("ebay.com");

    private final String url;

    MarketplaceProvider(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return url;
    }
}
