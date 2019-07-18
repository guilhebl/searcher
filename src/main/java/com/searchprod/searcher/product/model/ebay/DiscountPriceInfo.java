package com.searchprod.searcher.product.model.ebay;

import java.util.List;

public class DiscountPriceInfo {

    private List<PriceInfo> originalRetailPrice;
    private List<String> pricingTreatment;
    private List<String> soldOnEbay;
    private List<String> soldOffEbay;

    public List<PriceInfo> getOriginalRetailPrice() {
        return originalRetailPrice;
    }

    public void setOriginalRetailPrice(List<PriceInfo> originalRetailPrice) {
        this.originalRetailPrice = originalRetailPrice;
    }

    public List<String> getPricingTreatment() {
        return pricingTreatment;
    }

    public void setPricingTreatment(List<String> pricingTreatment) {
        this.pricingTreatment = pricingTreatment;
    }

    public List<String> getSoldOnEbay() {
        return soldOnEbay;
    }

    public void setSoldOnEbay(List<String> soldOnEbay) {
        this.soldOnEbay = soldOnEbay;
    }

    public List<String> getSoldOffEbay() {
        return soldOffEbay;
    }

    public void setSoldOffEbay(List<String> soldOffEbay) {
        this.soldOffEbay = soldOffEbay;
    }
}
