package com.searchprod.searcher.product.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import reactor.core.publisher.Mono;

public class LogRequestExchangeFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogRequestExchangeFilter.class);

    /**
     * This method returns filter function which will log request data
     **/
    public static ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            LOGGER.info("Request: {} {}", clientRequest.method(), clientRequest.url());
            clientRequest.headers().forEach((name, values) -> values.forEach(value -> LOGGER.info("{}={}", name, value)));
            return Mono.just(clientRequest);
        });
    }

}
