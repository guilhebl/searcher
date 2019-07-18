package com.searchprod.searcher.product.model;

import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.List;

public class ProductDetail {
    private Product product;
    private String description;
    private List<ImmutablePair<String, String>> attributes;
    private List<ProductDetailItem> productDetailItems;
    private List<ProductStat> productStats;

    public ProductDetail() {
    }

    public ProductDetail(Product product, String description, List<ImmutablePair<String, String>> attributes, List<ProductDetailItem> productDetailItems, List<ProductStat> productStats) {
        this.product = product;
        this.description = description;
        this.attributes = attributes;
        this.productDetailItems = productDetailItems;
        this.productStats = productStats;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ImmutablePair<String, String>> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<ImmutablePair<String, String>> attributes) {
        this.attributes = attributes;
    }

    public List<ProductDetailItem> getProductDetailItems() {
        return productDetailItems;
    }

    public void setProductDetailItems(List<ProductDetailItem> productDetailItems) {
        this.productDetailItems = productDetailItems;
    }

    public List<ProductStat> getProductStats() {
        return productStats;
    }

    public void setProductStats(List<ProductStat> productStats) {
        this.productStats = productStats;
    }
}
