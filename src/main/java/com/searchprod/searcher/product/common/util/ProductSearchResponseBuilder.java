package com.searchprod.searcher.product.common.util;

import com.searchprod.searcher.product.model.Product;
import com.searchprod.searcher.product.model.ProductSearchResponse;
import com.searchprod.searcher.product.model.ProductSearchResponseSummary;

import java.util.ArrayList;
import java.util.List;

public class ProductSearchResponseBuilder {

    public static ProductSearchResponse of(ProductSearchResponseSummary summary, List<Product> list) {
        return new ProductSearchResponse(
                summary,
                list
        );
    }

    public static ProductSearchResponseSummary ofSummary(Integer page, Integer pageCount, Integer totalCount) {
        return new ProductSearchResponseSummary(page, pageCount, totalCount);
    }

    public static ProductSearchResponse empty() {
        return of(
                ofSummary(1, 10, 0),
                new ArrayList<>()
        );
    }

    public static ProductSearchResponse merge(ProductSearchResponse a, ProductSearchResponse b) {
        a.getList().addAll(b.getList());
        a.getSummary().setPageCount(a.getSummary().getPageCount() + b.getSummary().getPageCount());
        a.getSummary().setTotalCount(a.getSummary().getTotalCount() + b.getSummary().getTotalCount());
        return a;
    }

}
