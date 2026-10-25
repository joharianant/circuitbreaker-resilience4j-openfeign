package com.aj.order;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "product-service",
        url = "${product.service.url}"
)
public interface ProductClient {

    @GetMapping("/products/{id}")
    ProductResponse getProduct(
            @PathVariable("id") Long id,
            @RequestParam("fail") boolean fail,
            @RequestParam("delayMs") long delayMs
    );
}
