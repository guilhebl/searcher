package com.searchprod.searcher.product.common.aop;

import com.searchprod.searcher.product.model.enumeration.MarketplaceProvider;

public interface RateLimiterService {
    boolean isRequestPossible(MarketplaceProvider provider);
}
