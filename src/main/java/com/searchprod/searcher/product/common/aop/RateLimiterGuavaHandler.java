package com.searchprod.searcher.product.common.aop;

import com.google.common.util.concurrent.RateLimiter;
import com.searchprod.searcher.product.model.enumeration.MarketplaceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Qualifier("rateLimiterGuavaHandler")
public class RateLimiterGuavaHandler implements RateLimiterService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RateLimiterGuavaHandler.class);

    private final Double walmartMaxCapacity;
    private final Double ebayMaxCapacity;
    private final Double bestBuyMaxCapacity;

    private Map<String, RateLimiter> map;

    public RateLimiterGuavaHandler(
            @Value("${walmart.ratelimit.max.capacity}") Double walmartMaxCapacity,
            @Value("${ebay.ratelimit.max.capacity}") Double ebayMaxCapacity,
            @Value("${bestbuy.ratelimit.max.capacity}") Double bestBuyMaxCapacity) {
        this.walmartMaxCapacity = walmartMaxCapacity;
        this.ebayMaxCapacity = ebayMaxCapacity;
        this.bestBuyMaxCapacity = bestBuyMaxCapacity;
        this.map = new ConcurrentHashMap<>();
    }

    @Override
    public boolean isRequestPossible(MarketplaceProvider provider) {
        LOGGER.info("request for " + provider);
        return isTimeSlotFree(provider);
    }

    private boolean isTimeSlotFree(MarketplaceProvider provider) {
        map.computeIfAbsent(provider.toString(), x -> RateLimiter.create(getMaxCapacity(provider)));
        return map.get(provider.toString()).tryAcquire();
    }

    private double getMaxCapacity(MarketplaceProvider provider) {
        switch (provider) {
            case WALMART: return walmartMaxCapacity;
            case BEST_BUY: return bestBuyMaxCapacity;
            case EBAY: return ebayMaxCapacity;
            default: throw new IllegalArgumentException("Error. Invalid marketplace provider");
        }
    }
}
