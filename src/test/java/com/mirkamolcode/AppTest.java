package com.mirkamolcode;


import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;

import java.time.Clock;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.verify;

public class AppTest {
    private final List<String> myMock = mock();
    private final List<String> names = new ArrayList<>();

    @Test
    void myFirstTestWithMockito() {
        myMock.add("Hello");
        when(myMock.get(0)).thenReturn("Hello");

        verify(myMock).add("Hello");

        assertThat(myMock.get(0)).isEqualTo("Hello");
    }

    @Test
    void myFirstTestWithOutMockito() {
        names.add("Hello");
        assertThat(names).hasSize(1);
    }

    @Test
    void shouldVerifyNoInteractions() {
        // given
        List<String> listMock = mock();
        // when
        // then
        verifyNoInteractions(listMock);
    }

    @Test
    void shouldVerifyNoMoreInteractions() {
        // given
        List<String> listMock = mock();
        // when
        listMock.add("hello");
        // then
        verify(listMock).add("hello");
        verifyNoMoreInteractions(listMock);
    }

    @Test
    void shouldVerifyInteractionsMode() {
        // given
        List<String> listMock = mock();
        // when
        listMock.clear();
        listMock.clear();
        // then
        verify(listMock, times(2)).clear();
        verifyNoMoreInteractions(listMock);
    }

    @Test
    void mockitoBDD() {
        // given
        List<String> mockList = mock();
//        when(mockList.get(0)).thenReturn("hello");
        given(mockList.get(0)).willReturn("hello");
        // when
        String actual = mockList.get(0);
        // then
//        verify(mockList).get(0);
        then(mockList).should().get(0);
        assertThat(actual).isEqualTo("hello");

    }

    @Test
    void chainedStubbing() {
        // given
        List<String> mockList = mock();
        // when
        given(mockList.size()).willReturn(1, 2, 3, 4);
        // then
        assertThat(mockList.size()).isEqualTo(1);
        assertThat(mockList.size()).isEqualTo(2);
        assertThat(mockList.size()).isEqualTo(3);
        assertThat(mockList.size()).isEqualTo(4); // moving forward will always be 4
        assertThat(mockList.size()).isEqualTo(4);
    }

    @Test
    void shouldReturnCustomAnswer() {
        // given
        List<String> mockList = mock();
        // when
        given(mockList.get(anyInt())).willAnswer(i->{
            int index = i.getArgument(0);
            return "Amigos: " + index;
        });
        // then
       assertThat(mockList.get(0)).isEqualTo("Amigos: 0");
       assertThat(mockList.get(1)).isEqualTo("Amigos: 1");
    }

    @Test
    void async() {
        // given
        Runnable mockRunnable = mock();
        // when
        Executors.newSingleThreadScheduledExecutor()
                .schedule(mockRunnable, 200, TimeUnit.MILLISECONDS);
        // then
        BDDMockito.then(mockRunnable).should(timeout(500).times(1)).run();
    }

    @Test
    void canAdvanceClock() {
        // given
        Clock clock = mock();
        ZoneId zoneId = ZoneId.of("Asia/Tashkent");
        ZonedDateTime fixedZdt = ZonedDateTime.of(
                2025, 1, 1,
                0, 0,
                0, 0,
                zoneId);
        given(clock.getZone()).willReturn(zoneId);
        given(clock.instant()).willReturn(fixedZdt.toInstant());

        ZonedDateTime now = ZonedDateTime.now(clock);
        System.out.println(now);

        // advance clock
        given(clock.instant()).willReturn(now.plusMinutes(15).toInstant());
        // then
        System.out.println(ZonedDateTime.now(clock));
    }
}
