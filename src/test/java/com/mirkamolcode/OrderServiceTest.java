package com.mirkamolcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class OrderServiceTest {
    private PaymentProcessor stripePaymentProcessor;
    private OrderService underTest;

    @BeforeEach
    void setUp() {
        stripePaymentProcessor = mock();
        underTest = new OrderService(stripePaymentProcessor);
    }

    @Test
    void canCharge() {
        // given
        BigDecimal amount = BigDecimal.TEN;
        when(stripePaymentProcessor.charge(amount)).thenReturn(true);
        // when
        boolean actual = underTest.processOrder(amount);
        // then
        Mockito.verify(stripePaymentProcessor).charge(BigDecimal.TEN);
        assertThat(actual).isTrue();
    }
}