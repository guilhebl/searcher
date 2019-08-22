package com.searchprod.searcher.product.model.ebay;

import java.util.List;

//@JsonIgnoreProperties(ignoreUnknown=true)
public class ShippingInfo {
    private List<PriceInfo> shippingServiceCost;
    private List<String> shippingType;
    private List<String> shipToLocations;
    private List<String> expeditedShipping;
    private List<String> oneDayShippingAvailable;
    private List<String> handlingTime;

    public List<PriceInfo> getShippingServiceCost() {
        return shippingServiceCost;
    }

    public void setShippingServiceCost(List<PriceInfo> shippingServiceCost) {
        this.shippingServiceCost = shippingServiceCost;
    }

    public List<String> getShippingType() {
        return shippingType;
    }

    public void setShippingType(List<String> shippingType) {
        this.shippingType = shippingType;
    }

    public List<String> getShipToLocations() {
        return shipToLocations;
    }

    public void setShipToLocations(List<String> shipToLocations) {
        this.shipToLocations = shipToLocations;
    }

    public List<String> getExpeditedShipping() {
        return expeditedShipping;
    }

    public void setExpeditedShipping(List<String> expeditedShipping) {
        this.expeditedShipping = expeditedShipping;
    }

    public List<String> getOneDayShippingAvailable() {
        return oneDayShippingAvailable;
    }

    public void setOneDayShippingAvailable(List<String> oneDayShippingAvailable) {
        this.oneDayShippingAvailable = oneDayShippingAvailable;
    }

    public List<String> getHandlingTime() {
        return handlingTime;
    }

    public void setHandlingTime(List<String> handlingTime) {
        this.handlingTime = handlingTime;
    }
}
