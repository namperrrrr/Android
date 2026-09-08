package com.example.dienmayxanh.features.customers.domain;
import com.example.dienmayxanh.features.customers.data.Customer;
import com.example.dienmayxanh.features.customers.data.CustomerRepository;
import java.util.List;

public class GetCustomersUseCase {
    private CustomerRepository repository = new CustomerRepository();
    public void execute(CustomerRepository.CustomerCallback<List<Customer>> callback) { repository.getCustomers(callback); }
}