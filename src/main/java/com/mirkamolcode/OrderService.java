package com.mirkamolcode;

import java.math.BigDecimal;

public class OrderService {
    private final PaymentProcessor paymentProcessor;

    public OrderService(PaymentProcessor paymentService) {
        this.paymentProcessor = paymentService;
    }

    public boolean processOrder(BigDecimal amount){
        boolean isCharged = paymentProcessor.charge(amount);
        return isCharged;
    }
}
