package com.searchprod.searcher.product.model.ebay;

import java.util.List;

//@JsonIgnoreProperties(ignoreUnknown=true)
public class SellingStatus {
    private List<String> bidCount;
    private List<PriceInfo> currentPrice;
    private List<PriceInfo> convertedCurrentPrice;
    private List<String> sellingState;
    private List<String> timeLeft;

    public List<String> getBidCount() {
        return bidCount;
    }

    public void setBidCount(List<String> bidCount) {
        this.bidCount = bidCount;
    }

    public List<PriceInfo> getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(List<PriceInfo> currentPrice) {
        this.currentPrice = currentPrice;
    }

    public List<PriceInfo> getConvertedCurrentPrice() {
        return convertedCurrentPrice;
    }

    public void setConvertedCurrentPrice(List<PriceInfo> convertedCurrentPrice) {
        this.convertedCurrentPrice = convertedCurrentPrice;
    }

    public List<String> getSellingState() {
        return sellingState;
    }

    public void setSellingState(List<String> sellingState) {
        this.sellingState = sellingState;
    }

    public List<String> getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(List<String> timeLeft) {
        this.timeLeft = timeLeft;
    }
}
