package com.searchprod.searcher.product.model.bestbuy;

public class BestBuyTrendingItem {
    private CustomerReviews customerReviews;
    private Descriptions descriptions;
    private ProductImages images;
    private ProductLinks links;
    private ProductNames names;
    private ProductPrices prices;
    private Integer rank;
    private String sku;

    public CustomerReviews getCustomerReviews() {
        return customerReviews;
    }

    public void setCustomerReviews(CustomerReviews customerReviews) {
        this.customerReviews = customerReviews;
    }

    public Descriptions getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(Descriptions descriptions) {
        this.descriptions = descriptions;
    }

    public ProductImages getImages() {
        return images;
    }

    public void setImages(ProductImages images) {
        this.images = images;
    }

    public ProductLinks getLinks() {
        return links;
    }

    public void setLinks(ProductLinks links) {
        this.links = links;
    }

    public ProductNames getNames() {
        return names;
    }

    public void setNames(ProductNames names) {
        this.names = names;
    }

    public ProductPrices getPrices() {
        return prices;
    }

    public void setPrices(ProductPrices prices) {
        this.prices = prices;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }
}
