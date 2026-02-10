package com.mirkamolcode.payment;

import java.math.BigDecimal;

public interface PaymentProcessor {
    boolean charge(BigDecimal bigDecimal);
}
