package com.aj.order;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{productId}")
    public ProductResponse getOrderProduct(
            @PathVariable Long productId,
            @RequestParam(defaultValue = "false") boolean fail,
            @RequestParam(defaultValue = "0") long delayMs) {

        return orderService.getProduct(productId, fail, delayMs);
    }
}
