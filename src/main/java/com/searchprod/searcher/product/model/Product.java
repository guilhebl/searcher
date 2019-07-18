package com.searchprod.searcher.product.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {
    private String id;
    private String upc;
    private String name;
    private String sourceName;
    private String sourceItemDetailViewUrl;
    private String imageUrl;
    private String sourceImageUrl;
    private BigDecimal price;
    private String category;
    private Integer numReviews;
    private Float rating;

    public Product() {
    }

    public Product(String id, String upc, String name, String sourceName, String sourceItemDetailViewUrl, String imageUrl, String sourceImageUrl, BigDecimal price, String category, Integer numReviews, Float rating) {
        this.id = id;
        this.upc = upc;
        this.name = name;
        this.sourceName = sourceName;
        this.sourceItemDetailViewUrl = sourceItemDetailViewUrl;
        this.imageUrl = imageUrl;
        this.sourceImageUrl = sourceImageUrl;
        this.price = price;
        this.category = category;
        this.numReviews = numReviews;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public String getSourceItemDetailViewUrl() {
        return sourceItemDetailViewUrl;
    }

    public void setSourceItemDetailViewUrl(String sourceItemDetailViewUrl) {
        this.sourceItemDetailViewUrl = sourceItemDetailViewUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) &&
                Objects.equals(upc, product.upc) &&
                Objects.equals(name, product.name) &&
                Objects.equals(sourceName, product.sourceName) &&
                Objects.equals(sourceItemDetailViewUrl, product.sourceItemDetailViewUrl) &&
                Objects.equals(imageUrl, product.imageUrl) &&
                Objects.equals(sourceImageUrl, product.sourceImageUrl) &&
                Objects.equals(price, product.price) &&
                Objects.equals(category, product.category) &&
                Objects.equals(numReviews, product.numReviews) &&
                Objects.equals(rating, product.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, upc, name, sourceName, sourceItemDetailViewUrl, imageUrl, sourceImageUrl, price, category, numReviews, rating);
    }
}
