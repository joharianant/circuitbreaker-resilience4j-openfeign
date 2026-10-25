package com.aj.order;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private static final Logger log =
            LoggerFactory.getLogger(OrderService.class);

    private final ProductClient productClient;

    public OrderService(ProductClient productClient) {
        this.productClient = productClient;
    }

    @CircuitBreaker(
            name = "productService",
            fallbackMethod = "getProductFallback"
    )
    @Retry(name = "productService")
    public ProductResponse getProduct(
            Long id,
            boolean fail,
            long delayMs) {

        log.info("Calling product-service for product ID: {}", id);

        return productClient.getProduct(id, fail, delayMs);
    }

    public ProductResponse getProductFallback(
            Long id,
            boolean fail,
            long delayMs,
            Throwable exception) {

        log.warn(
                "Fallback triggered for product ID {}. Reason: {}",
                id,
                exception.toString()
        );

        return new ProductResponse(
                id,
                "Product temporarily unavailable",
                null,
                null,
                "fallback"
        );
    }
}
