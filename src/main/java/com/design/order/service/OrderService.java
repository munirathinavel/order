package com.design.order.service;

import com.design.order.exception.DuplicateOrderNoException;
import com.design.order.model.Order;
import com.design.order.repository.OrderRepository;
import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Timed(value = "orderService.save", description = "Time taken to save an order in the service layer")
    public Order save(Order order) {
        try {
            return orderRepository.save(order);
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("Duplicate entry")) {
                throw new DuplicateOrderNoException("OrderNo already exists: " + order.getOrderNo());
            }
            throw e;
        }
    }

    @Timed(value = "orderService.findById", description = "Time taken to find an order by ID in the service layer")
    public Optional<Order> findById(long orderId) {
        return orderRepository.findById(orderId);
    }

    @Timed(value = "orderService.findAll", description = "Time taken to find all orders in the service layer")
    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    @Timed(value = "orderService.findByOrderNo", description = "Time taken to find an order by order number in the service layer")
    public Optional<Order> findByOrderNo(String orderNo) {
        return orderRepository.findByOrderNo(orderNo);
    }
}
