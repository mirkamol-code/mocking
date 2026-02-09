package com.mirkamolcode;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
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
    @Captor
    private ArgumentCaptor<Order> orderArgumentCaptor;

    @Test
    void canChargeWithAssertCaptor() {
        // given
        BigDecimal amount = BigDecimal.TEN;
        User user = new User(1, "James");
        when(stripePaymentProcessor.charge(amount)).thenReturn(true);
        when(orderRepository.save(any())).thenReturn(1);
        // when
        boolean actual = underTest.processOrder(user, amount);
        // then
        verify(stripePaymentProcessor).charge(BigDecimal.TEN);

//        ArgumentCaptor<Order> orderArgumentCaptor = ArgumentCaptor.forClass(Order.class);

        verify(orderRepository).save(orderArgumentCaptor.capture());

        Order orderArgumentCaptureValue = orderArgumentCaptor.getValue();
        assertThat(orderArgumentCaptureValue.amount()).isEqualTo(amount);
        assertThat(orderArgumentCaptureValue.user()).isEqualTo(user);
        assertThat(orderArgumentCaptureValue.id()).isNotNull();
        assertThat(orderArgumentCaptureValue.zonedDateTime())
                .isBefore(ZonedDateTime.now())
                .isNotNull();
        assertThat(actual).isTrue();

    }

    @Test
    void canChargeWithAssertArg() {
        // given
        BigDecimal amount = BigDecimal.TEN;
        User user = new User(1, "James");
        when(stripePaymentProcessor.charge(amount)).thenReturn(true);
        when(orderRepository.save(any())).thenReturn(1);
        // when
        boolean actual = underTest.processOrder(user, amount);
        // then
        verify(stripePaymentProcessor).charge(BigDecimal.TEN);
        verify(orderRepository).save(assertArg(order -> {
            assertThat(order.amount()).isEqualTo(amount);
            assertThat(order.user()).isEqualTo(user);
            assertThat(order.id()).isNotNull();
            assertThat(order.zonedDateTime())
                    .isBefore(ZonedDateTime.now())
                    .isNotNull();
        }));
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