package com.example.dienmayxanh.features.orders.domain;

public class OrderUseCases {
    public GetOrdersUseCase getOrders = new GetOrdersUseCase();
    public AddOrderUseCase addOrder = new AddOrderUseCase();
    public UpdateOrderUseCase updateOrder = new UpdateOrderUseCase();
    public DeleteOrderUseCase deleteOrder = new DeleteOrderUseCase();
}