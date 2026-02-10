package com.mirkamolcode.payment;

import java.math.BigDecimal;

public class StripePaymentProcessor implements PaymentProcessor {
    @Override
    public boolean charge(BigDecimal bigDecimal) {
        return false;
    }
}
