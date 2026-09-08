package com.example.dienmayxanh.features.orders.domain;

import com.example.dienmayxanh.features.orders.data.OrderRepository;

public class DeleteOrderUseCase {
    private OrderRepository repository = new OrderRepository();

    public void execute(String orderId, OrderRepository.OrderCallback<String> callback) {
        repository.deleteOrder(orderId, callback);
    }
}