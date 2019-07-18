package com.searchprod.searcher.product.common.aop;

import com.searchprod.searcher.product.model.enumeration.MarketplaceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A simple implementation of the Request Monitor using a static interval for each call, for example if API supports 5 calls
 * per second then this monitor will ensure each call is done in intervals of at least 200ms.
 * This is a very simple approach which performs well for long running jobs that have thousands of calls,
 * however it won't handle very well burst use-case scenarios. For those scenarios use a different implementation.
 *
 */
@Component
@Qualifier("rateLimiterFixedCycle")
public class RateLimiterFixedCycle implements RateLimiterService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RateLimiterFixedCycle.class);

    private final Integer maxRetries;
    private final Long apiWaitTimeWalmart;
    private final Long apiWaitTimeEbay;
    private final Long apiWaitTimeBestBuy;

    private Map<String, Long> map;

    public RateLimiterFixedCycle(
            @Value("${api.marketplace.max.retries}") Integer maxRetries,
            @Value("${walmart.api.wait.interval}") Long apiWaitTimeWalmart,
            @Value("${ebay.api.wait.interval}") Long apiWaitTimeEbay,
            @Value("${bestbuy.api.wait.interval}") Long apiWaitTimeBestBuy) {
        this.maxRetries = maxRetries;
        this.apiWaitTimeWalmart = apiWaitTimeWalmart;
        this.apiWaitTimeEbay = apiWaitTimeEbay;
        this.apiWaitTimeBestBuy = apiWaitTimeBestBuy;
        this.map = new ConcurrentHashMap<>();
    }

    @Override
    public boolean isRequestPossible(MarketplaceProvider provider) {
        LOGGER.info("request for " + provider);
        return isTimeSlotFree(provider);
    }

    private boolean isTimeSlotFree(MarketplaceProvider provider) {
        var now = Instant.now().toEpochMilli();
        var last = map.getOrDefault(provider.toString(), 0L);
        var diff = now - last;
        if (diff >= getWaitTime(provider)) {
            setKeyValue(provider, now);
            return true;
        }
        return false;
    }

    private void setKeyValue(MarketplaceProvider provider, long now) {
        map.put(provider.toString(), now);
    }

    private long getWaitTime(MarketplaceProvider provider) {
        switch (provider) {
            case WALMART: return apiWaitTimeWalmart;
            case BEST_BUY: return apiWaitTimeBestBuy;
            case EBAY: return apiWaitTimeEbay;
            default: throw new IllegalArgumentException("Error. Invalid marketplace provider");
        }
    }
}
