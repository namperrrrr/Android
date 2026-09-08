package com.example.dienmayxanh.features.customers.domain;
import com.example.dienmayxanh.features.customers.data.CustomerRepository;

public class DeleteCustomerUseCase {
    private CustomerRepository repository = new CustomerRepository();
    public void execute(String customerId, CustomerRepository.CustomerCallback<String> callback) { repository.deleteCustomer(customerId, callback); }
}