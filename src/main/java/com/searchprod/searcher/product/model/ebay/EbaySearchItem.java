package com.searchprod.searcher.product.model.ebay;

import java.util.List;

// @JsonIgnoreProperties(ignoreUnknown=true)
public class EbaySearchItem {

    private List<ProductId> productId;
    private List<String> itemId;
    private List<String> title;
    private List<String> subtitle;
    private List<String> globalId;
    private List<String> galleryURL;
    private List<String> viewItemURL;
    private List<String> paymentMethod;
    private List<String> autoPay;
    private List<String> postalCode;
    private List<String> location;
    private List<String> country;
    private List<PrimaryCategory> primaryCategory;
    private List<PrimaryCategory> secondaryCategory;
    private List<ShippingInfo> shippingInfo;
    private List<SellingStatus> sellingStatus;
    private List<ListingInfo> listingInfo;
    private List<DiscountPriceInfo> discountPriceInfo;
    private List<String> returnsAccepted;
    private List<String> galleryPlusPictureURL;
    private List<String> pictureURLLarge;
    private List<ItemCondition> condition;
    private List<String> isMultiVariationListing;
    private List<String> topRatedListing;
    private List<String> charityId;

    public List<ProductId> getProductId() {
        return productId;
    }

    public void setProductId(List<ProductId> productId) {
        this.productId = productId;
    }

    public List<String> getItemId() {
        return itemId;
    }

    public void setItemId(List<String> itemId) {
        this.itemId = itemId;
    }

    public List<String> getTitle() {
        return title;
    }

    public void setTitle(List<String> title) {
        this.title = title;
    }

    public List<String> getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(List<String> subtitle) {
        this.subtitle = subtitle;
    }

    public List<String> getGlobalId() {
        return globalId;
    }

    public void setGlobalId(List<String> globalId) {
        this.globalId = globalId;
    }

    public List<String> getGalleryURL() {
        return galleryURL;
    }

    public void setGalleryURL(List<String> galleryURL) {
        this.galleryURL = galleryURL;
    }

    public List<String> getViewItemURL() {
        return viewItemURL;
    }

    public void setViewItemURL(List<String> viewItemURL) {
        this.viewItemURL = viewItemURL;
    }

    public List<String> getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(List<String> paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public List<String> getAutoPay() {
        return autoPay;
    }

    public void setAutoPay(List<String> autoPay) {
        this.autoPay = autoPay;
    }

    public List<String> getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(List<String> postalCode) {
        this.postalCode = postalCode;
    }

    public List<String> getLocation() {
        return location;
    }

    public void setLocation(List<String> location) {
        this.location = location;
    }

    public List<String> getCountry() {
        return country;
    }

    public void setCountry(List<String> country) {
        this.country = country;
    }

    public List<PrimaryCategory> getPrimaryCategory() {
        return primaryCategory;
    }

    public void setPrimaryCategory(List<PrimaryCategory> primaryCategory) {
        this.primaryCategory = primaryCategory;
    }

    public List<ShippingInfo> getShippingInfo() {
        return shippingInfo;
    }

    public void setShippingInfo(List<ShippingInfo> shippingInfo) {
        this.shippingInfo = shippingInfo;
    }

    public List<SellingStatus> getSellingStatus() {
        return sellingStatus;
    }

    public void setSellingStatus(List<SellingStatus> sellingStatus) {
        this.sellingStatus = sellingStatus;
    }

    public List<ListingInfo> getListingInfo() {
        return listingInfo;
    }

    public void setListingInfo(List<ListingInfo> listingInfo) {
        this.listingInfo = listingInfo;
    }

    public List<DiscountPriceInfo> getDiscountPriceInfo() {
        return discountPriceInfo;
    }

    public void setDiscountPriceInfo(List<DiscountPriceInfo> discountPriceInfo) {
        this.discountPriceInfo = discountPriceInfo;
    }

    public List<String> getReturnsAccepted() {
        return returnsAccepted;
    }

    public void setReturnsAccepted(List<String> returnsAccepted) {
        this.returnsAccepted = returnsAccepted;
    }

    public List<String> getGalleryPlusPictureURL() {
        return galleryPlusPictureURL;
    }

    public void setGalleryPlusPictureURL(List<String> galleryPlusPictureURL) {
        this.galleryPlusPictureURL = galleryPlusPictureURL;
    }

    public List<String> getPictureURLLarge() {
        return pictureURLLarge;
    }

    public void setPictureURLLarge(List<String> pictureURLLarge) {
        this.pictureURLLarge = pictureURLLarge;
    }

    public List<ItemCondition> getCondition() {
        return condition;
    }

    public void setCondition(List<ItemCondition> condition) {
        this.condition = condition;
    }

    public List<String> getIsMultiVariationListing() {
        return isMultiVariationListing;
    }

    public void setIsMultiVariationListing(List<String> isMultiVariationListing) {
        this.isMultiVariationListing = isMultiVariationListing;
    }

    public List<String> getTopRatedListing() {
        return topRatedListing;
    }

    public void setTopRatedListing(List<String> topRatedListing) {
        this.topRatedListing = topRatedListing;
    }

    public List<PrimaryCategory> getSecondaryCategory() {
        return secondaryCategory;
    }

    public void setSecondaryCategory(List<PrimaryCategory> secondaryCategory) {
        this.secondaryCategory = secondaryCategory;
    }

    public List<String> getCharityId() {
        return charityId;
    }

    public void setCharityId(List<String> charityId) {
        this.charityId = charityId;
    }
}
