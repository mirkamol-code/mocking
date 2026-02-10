package com.mirkamolcode.model;

import java.time.ZonedDateTime;

public record VerificationCode(
        String code,
        ZonedDateTime createdAt
) {
}
