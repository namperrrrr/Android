package com.example.dienmayxanh.features.customers.domain;

public class CustomerUseCases {
    public GetCustomersUseCase getCustomers = new GetCustomersUseCase();
    public AddCustomerUseCase addCustomer = new AddCustomerUseCase();
    public UpdateCustomerUseCase updateCustomer = new UpdateCustomerUseCase();
    public DeleteCustomerUseCase deleteCustomer = new DeleteCustomerUseCase();
}