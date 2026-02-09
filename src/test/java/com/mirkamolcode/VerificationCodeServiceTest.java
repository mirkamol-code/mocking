package com.mirkamolcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VerificationCodeServiceTest {
    @Mock
    private Clock clock;


    private VerificationCodeService underTest;

    private final ZonedDateTime NOW = ZonedDateTime.of(
            2026, 1, 15,
            0, 5, 0, 0,
            ZoneId.of("Asia/Tashkent"));

    @BeforeEach
    void setUp() {
        when(clock.getZone()).thenReturn(NOW.getZone());
        when(clock.instant()).thenReturn(NOW.toInstant());
        underTest = new VerificationCodeService(clock, Duration.ofMinutes(4));
    }

    @Test
    void isExpired() {
        // given
        ZonedDateTime createdAt = NOW.minusMinutes(5);
        VerificationCode verificationCode = new VerificationCode("Hello", createdAt);
        // when
        boolean actual = underTest.isExpired(verificationCode);

        // then
        assertThat(actual).isTrue();
    }

    @Test
    void isNotExpired() {
        // given
        ZonedDateTime createdAt = NOW.minusMinutes(3);
        VerificationCode verificationCode = new VerificationCode("Hello", createdAt);
        // when
        boolean actual = underTest.isExpired(verificationCode);

        // then
        assertThat(actual).isFalse();
    }
}