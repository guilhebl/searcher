package com.searchprod.searcher.product.model.bestbuy;

import java.math.BigDecimal;

public class ProductPrices {

    private BigDecimal current;
    private BigDecimal regular;

    public BigDecimal getCurrent() {
        return current;
    }

    public void setCurrent(BigDecimal current) {
        this.current = current;
    }

    public BigDecimal getRegular() {
        return regular;
    }

    public void setRegular(BigDecimal regular) {
        this.regular = regular;
    }
}
