package io.apptales.minipos.service;

import reactor.core.publisher.Flux;

public interface ProductStockStreamingService {

    /**
     * Stream real-time stock updates for a single product.
     */
    Flux<String> streamStockUpdates(String productId);

    /**
     * Stream real-time transaction activities for a single product.
     */
    Flux<String> streamTransactionActivities(String productId);

    /**
     * Stream potential revenue impact for a single product.
     */
    void streamRevenueImpact(String productId);

    /**
     * Stream stock movement frequency and inactivity alerts for a single product.
     */
    void streamStockStatus(String productId);

}
