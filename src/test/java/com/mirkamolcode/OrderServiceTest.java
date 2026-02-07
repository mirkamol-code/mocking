package com.mirkamolcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock
    private PaymentProcessor stripePaymentProcessor;
    @InjectMocks
    private OrderService underTest;



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