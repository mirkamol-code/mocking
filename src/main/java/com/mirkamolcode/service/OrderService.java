package com.mirkamolcode.service;

import com.mirkamolcode.model.Order;
import com.mirkamolcode.model.User;
import com.mirkamolcode.payment.PaymentProcessor;
import com.mirkamolcode.repository.OrderRepository;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

public class OrderService {
    private final PaymentProcessor paymentProcessor;
    private final OrderRepository orderRepository;

    public OrderService(PaymentProcessor paymentService, OrderRepository orderRepository) {
        this.paymentProcessor = paymentService;
        this.orderRepository = orderRepository;
    }

    public boolean processOrder(User user, BigDecimal amount){
        boolean isCharged = paymentProcessor.charge(amount);
        if (!isCharged){
            throw new IllegalStateException("Payment failed");
        }
        Order order = new Order(
                UUID.randomUUID(),
                user,
                amount,
                ZonedDateTime.now()
        );
        int saveResult = orderRepository.save(order);
        return saveResult == 1;
    }
}
