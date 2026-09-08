package com.example.dienmayxanh.features.orders.domain;

import com.example.dienmayxanh.features.orders.data.Order;
import com.example.dienmayxanh.features.orders.data.OrderRepository;
import java.util.List;

public class GetOrdersUseCase {
    private OrderRepository repository = new OrderRepository();

    public void execute(OrderRepository.OrderCallback<List<Order>> callback) {
        repository.getOrders(callback);
    }
}