package com.example.dienmayxanh.features.orders.domain;

import com.example.dienmayxanh.features.orders.data.Order;
import com.example.dienmayxanh.features.orders.data.OrderRepository;

public class AddOrderUseCase {
    private OrderRepository repository = new OrderRepository();

    public void execute(Order order, OrderRepository.OrderCallback<String> callback) {
        repository.addOrder(order, callback);
    }
}