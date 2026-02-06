package com.mirkamolcode;


import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class AppTest {
    private final List<String> myMock = mock();
    private final List<String> names = new ArrayList<>();

    @Test
    void myFirstTestWithMockito() {
        myMock.add("Hello");
        when(myMock.get(0)).thenReturn("Hello");

        verify(myMock).add("Hello");

        String actual = myMock.get(0);
        assertThat(actual).isEqualTo("Hello");
    }

    @Test
    void myFirstTestWithOutMockito() {
        names.add("Hello");
        assertThat(names).hasSize(1);

    }
}
