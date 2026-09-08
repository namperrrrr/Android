package com.example.dienmayxanh.features.customers.domain;
import com.example.dienmayxanh.features.customers.data.Customer;
import com.example.dienmayxanh.features.customers.data.CustomerRepository;

public class UpdateCustomerUseCase {
    private CustomerRepository repository = new CustomerRepository();
    public void execute(Customer customer, CustomerRepository.CustomerCallback<String> callback) { repository.updateCustomer(customer, callback); }
}