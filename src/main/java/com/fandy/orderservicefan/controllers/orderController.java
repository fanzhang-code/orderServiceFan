package com.fandy.orderservicefan.controllers;

import com.fandy.orderservicefan.requests.CreateOrderRequest;
import com.fandy.orderservicefan.responses.CreateOrderResponse;
import com.fandy.orderservicefan.responses.GetOrderResponse;
import com.fandy.orderservicefan.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class orderController {

    private final OrderService orderService;

    public orderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<CreateOrderResponse> createOrder(
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            @RequestHeader(value = "X-Debug-Fail-After-Commit", defaultValue = "false") boolean failureTrigger,
            @RequestBody CreateOrderRequest request) {
        CreateOrderResponse response = orderService.createOrder(request, idempotencyKey, failureTrigger);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping("/{orderId}")
    public ResponseEntity<GetOrderResponse> getOrder(@PathVariable String orderId) {
        GetOrderResponse order = orderService.getOrderDetail(orderId);
        return ResponseEntity.ok(order);
    }
}
