package com.searchprod.searcher.product.model.bestbuy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BestBuySearchItem {

    private String productId;
    private String upc;
    private String sku;
    private String name;
    private BigDecimal salePrice;
    private String releaseDate;
    private String url;
    private String image;
    private String thumbnailImage;
    private String manufacturer;
    private String department;
    private String customerReviewAverage;
    private Integer customerReviewCount;
    private List<CategoryPath> categoryPath = new ArrayList<>();
    private Boolean newProduct;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getThumbnailImage() {
        return thumbnailImage;
    }

    public void setThumbnailImage(String thumbnailImage) {
        this.thumbnailImage = thumbnailImage;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getCustomerReviewAverage() {
        return customerReviewAverage;
    }

    public void setCustomerReviewAverage(String customerReviewAverage) {
        this.customerReviewAverage = customerReviewAverage;
    }

    public Integer getCustomerReviewCount() {
        return customerReviewCount;
    }

    public void setCustomerReviewCount(Integer customerReviewCount) {
        this.customerReviewCount = customerReviewCount;
    }

    public List<CategoryPath> getCategoryPath() {
        return categoryPath;
    }

    public void setCategoryPath(List<CategoryPath> categoryPath) {
        this.categoryPath = categoryPath;
    }

    public Boolean getNewProduct() {
        return newProduct;
    }

    public void setNewProduct(Boolean newProduct) {
        this.newProduct = newProduct;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }
}
