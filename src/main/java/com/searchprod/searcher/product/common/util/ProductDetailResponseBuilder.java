package com.searchprod.searcher.product.common.util;

import com.searchprod.searcher.product.model.Product;
import com.searchprod.searcher.product.model.ProductDetail;
import com.searchprod.searcher.product.model.ProductStat;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductDetailResponseBuilder {

    public static ProductDetail addDetailItem(ProductDetail a, ProductDetail b) {
        if (b != null && b.getProduct() != null) {
            a.getProductDetailItems().add(b.getProduct());
        }
        return a;
    }

    public static ProductDetail of(Product product) {
        return new ProductDetail(
                product,
                "",
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>()
        );
    }

    public static ProductDetail of(Product product, String description, List<ImmutablePair<String, String>> attributes, List<Product> productDetailItems, List<ProductStat> productStats) {
        return new ProductDetail(
                product,
                description,
                attributes,
                productDetailItems,
                productStats
        );
    }


    public static ProductDetail empty() {
        return of(
                null,
                "",
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList()
        );
    }


}
