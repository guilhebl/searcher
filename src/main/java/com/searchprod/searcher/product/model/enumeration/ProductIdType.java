package com.searchprod.searcher.product.model.enumeration;

public enum ProductIdType {
    ID("id"), UPC("upc");

    private final String name;

    ProductIdType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
