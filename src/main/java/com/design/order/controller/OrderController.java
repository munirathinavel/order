package com.design.order.controller;

import com.design.order.model.Order;
import com.design.order.service.OrderService;
import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Timed(value = "order.save", description = "Time taken to save an order")
    @PostMapping
    public Order saveOrder(@RequestBody Order order) {
        return orderService.save(order);
    }

    @Timed(value = "order.findById", description = "Time taken to find an order by ID")
    @GetMapping("/{orderId}")
    public Order findById(@PathVariable("orderId") long orderId) {
        Optional<Order> orderOptional = orderService.findById(orderId);
        return orderOptional.orElse(null);
    }

    @Timed(value = "order.findByOrderNo", description = "Time taken to find an order by order number")
    @GetMapping("/findByOrderNo")
    public Order findByOrderNo(@RequestParam String orderNo) {
        Optional<Order> orderOptional = orderService.findByOrderNo(orderNo);
        return orderOptional.orElse(null);
    }

    @Timed(value = "order.findAll", description = "Time taken to find all orders")
    @GetMapping
    public List<Order> findAllOrders() {
        return orderService.findAllOrders();
    }
}
