package com.mirkamolcode.repository;

import com.mirkamolcode.model.Order;

public interface OrderRepository {
    int save(Order order);
}
