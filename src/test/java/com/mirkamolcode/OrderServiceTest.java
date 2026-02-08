package com.mirkamolcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock
    private PaymentProcessor stripePaymentProcessor;
    @Mock
    private OrderRepository orderRepository;
    @InjectMocks
    private OrderService underTest;


    @Test
    void canCharge() {
        // given
        BigDecimal amount = BigDecimal.TEN;
        when(stripePaymentProcessor.charge(amount)).thenReturn(true);
        when(orderRepository.save(any())).thenReturn(1);
        // when
        boolean actual = underTest.processOrder(null, amount);
        // then
        verify(stripePaymentProcessor).charge(BigDecimal.TEN);
        assertThat(actual).isTrue();
    }

    @Test
    void shouldThrowWhenChargeFails() {
        // given
        BigDecimal amount = BigDecimal.TEN;
        when(stripePaymentProcessor.charge(amount)).thenReturn(false);
        // when
        assertThatThrownBy(() -> underTest.processOrder(null, amount))
                .hasMessageContaining("Payment failed")
                .isInstanceOf(IllegalStateException.class);
        // then
        verify(stripePaymentProcessor).charge(BigDecimal.TEN);
        verifyNoInteractions(orderRepository);
    }

    @Test
    void testAnyMatcher() {
        // given
        Map<String, String> mockMap = mock();
        // when
        when(mockMap.get(anyString())).thenReturn("Hello");
        // then
        assertThat(mockMap.get("1")).isEqualTo("Hello");
        assertThat(mockMap.get("2")).isEqualTo("Hello");
        verify(mockMap, times(2)).get(anyString());
    }

    @Test
    void testEqMatcher() {
        // given
        Map<String, String> mockMap = mock();
        // when
        when(mockMap.put(anyString(), eq("1"))).thenReturn("hello");
        // then
        String actual = mockMap.put("hello", "1");
        assertThat(actual).isEqualTo("hello");
        verify(mockMap).put(eq("hello"), eq("1"));

    }
}