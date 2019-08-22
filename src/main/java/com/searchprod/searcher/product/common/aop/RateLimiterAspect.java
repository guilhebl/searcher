package com.searchprod.searcher.product.common.aop;

import com.searchprod.searcher.product.common.exception.RateLimitException;
import com.searchprod.searcher.product.model.enumeration.MarketplaceProvider;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RateLimiterAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(RateLimiterAspect.class);

    private final RateLimiterService rateLimiterService;

    public RateLimiterAspect(@Qualifier("rateLimiterGuavaHandler") RateLimiterService rateLimiterService) {
        this.rateLimiterService = rateLimiterService;
    }

    @Before("execution(* com.searchprod.searcher.product.service.provider.*.*(..))")
    public void before(JoinPoint joinPoint) {
        var name = joinPoint.getSignature().getDeclaringType().getSimpleName();
        if (!rateLimiterService.isRequestPossible(getMarketplaceProvider(name))) {
            LOGGER.info("rate limit reached for {}", name);
            throw new RateLimitException(String.format("rate limit reached for %s", name));
        }
        LOGGER.info("request allowed for {}", name);
    }

    private MarketplaceProvider getMarketplaceProvider(String name) {
        switch (name) {
            case "EbayService" : return MarketplaceProvider.EBAY;
            case "WalmartService" : return MarketplaceProvider.WALMART;
            case "BestBuyService" : return MarketplaceProvider.BEST_BUY;
            default: throw new IllegalArgumentException("Error. Invalid marketplace provider");
        }
    }

}