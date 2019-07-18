package com.searchprod.searcher.product.model.walmart;

import java.time.Instant;

public class WalmartTrendingResponse extends WalmartSearchBaseResponse {
    private Instant time;

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }
}
