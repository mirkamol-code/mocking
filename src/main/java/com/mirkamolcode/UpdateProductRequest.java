package com.mirkamolcode;

import java.math.BigDecimal;
import java.util.Objects;

public record UpdateProductRequest(
        String name,
        String description,
        BigDecimal price,
        String imageUrl,
        Integer stockLevel,
        Boolean isPublished
){
}
