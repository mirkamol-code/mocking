package com.mirkamolcode;

import java.math.BigDecimal;

public interface PaymentProcessor {
    boolean charge(BigDecimal bigDecimal);
}
