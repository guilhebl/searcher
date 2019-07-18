package com.searchprod.searcher.product.model;

import java.math.BigDecimal;

public class ProductDetailItem {
    private String sourceName;
    private String sourceItemDetailViewUrl;
    private String sourceImageUrl;
    private BigDecimal price;
    private Integer numReviews;
    private Float rating;

    public ProductDetailItem() {
    }

    public ProductDetailItem(String sourceName, String sourceItemDetailViewUrl, String sourceImageUrl, BigDecimal price, Integer numReviews, Float rating) {
        this.sourceName = sourceName;
        this.sourceItemDetailViewUrl = sourceItemDetailViewUrl;
        this.sourceImageUrl = sourceImageUrl;
        this.price = price;
        this.numReviews = numReviews;
        this.rating = rating;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getSourceItemDetailViewUrl() {
        return sourceItemDetailViewUrl;
    }

    public void setSourceItemDetailViewUrl(String sourceItemDetailViewUrl) {
        this.sourceItemDetailViewUrl = sourceItemDetailViewUrl;
    }

    public String getSourceImageUrl() {
        return sourceImageUrl;
    }

    public void setSourceImageUrl(String sourceImageUrl) {
        this.sourceImageUrl = sourceImageUrl;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getNumReviews() {
        return numReviews;
    }

    public void setNumReviews(Integer numReviews) {
        this.numReviews = numReviews;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }
}
