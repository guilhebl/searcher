package com.searchprod.searcher.product.model.ebay;

import java.util.List;

public class ListingInfo {
    private List<String> buyItNowAvailable;
    private List<String> startTime;
    private List<String> endTime;
    private List<String> listingType;
    private List<String> gift;
    private List<String> watchCount;
    private List<String> bestOfferEnabled;
    private List<PriceInfo> buyItNowPrice;
    private List<PriceInfo> convertedBuyItNowPrice;

    public List<String> getBuyItNowAvailable() {
        return buyItNowAvailable;
    }

    public void setBuyItNowAvailable(List<String> buyItNowAvailable) {
        this.buyItNowAvailable = buyItNowAvailable;
    }

    public List<String> getStartTime() {
        return startTime;
    }

    public void setStartTime(List<String> startTime) {
        this.startTime = startTime;
    }

    public List<String> getEndTime() {
        return endTime;
    }

    public void setEndTime(List<String> endTime) {
        this.endTime = endTime;
    }

    public List<String> getListingType() {
        return listingType;
    }

    public void setListingType(List<String> listingType) {
        this.listingType = listingType;
    }

    public List<String> getGift() {
        return gift;
    }

    public void setGift(List<String> gift) {
        this.gift = gift;
    }

    public List<String> getWatchCount() {
        return watchCount;
    }

    public void setWatchCount(List<String> watchCount) {
        this.watchCount = watchCount;
    }

    public List<String> getBestOfferEnabled() {
        return bestOfferEnabled;
    }

    public void setBestOfferEnabled(List<String> bestOfferEnabled) {
        this.bestOfferEnabled = bestOfferEnabled;
    }

    public List<PriceInfo> getBuyItNowPrice() {
        return buyItNowPrice;
    }

    public void setBuyItNowPrice(List<PriceInfo> buyItNowPrice) {
        this.buyItNowPrice = buyItNowPrice;
    }

    public List<PriceInfo> getConvertedBuyItNowPrice() {
        return convertedBuyItNowPrice;
    }

    public void setConvertedBuyItNowPrice(List<PriceInfo> convertedBuyItNowPrice) {
        this.convertedBuyItNowPrice = convertedBuyItNowPrice;
    }
}
