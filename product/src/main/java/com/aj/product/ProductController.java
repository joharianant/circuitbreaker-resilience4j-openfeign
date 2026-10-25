package com.aj.product;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {

    @GetMapping("/{id}")
    public Map<String, Object> getProduct(
            @PathVariable Long id,
            @RequestParam(defaultValue = "false") boolean fail,
            @RequestParam(defaultValue = "0") long delayMs) {

        if (fail) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Simulated product service failure"
            );
        }

        if (delayMs > 0) {
            try {
                Thread.sleep(Math.min(delayMs, 10_000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new ResponseStatusException(
                        HttpStatus.SERVICE_UNAVAILABLE,
                        "Request interrupted"
                );
            }
        }

        return Map.of(
                "id", id,
                "name", "Laptop",
                "price", new BigDecimal("75000.00"),
                "currency", "INR",
                "service", "product-service"
        );
    }
}
