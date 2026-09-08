package com.example.dienmayxanh.features.customers.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dienmayxanh.core.network.Resource;
import com.example.dienmayxanh.features.customers.data.Customer;
import com.example.dienmayxanh.features.customers.domain.CustomerUseCases;

import java.util.List;

public class CustomerViewModel extends ViewModel {
    private CustomerUseCases useCases = new CustomerUseCases();

    private MutableLiveData<Resource<List<Customer>>> customerListState = new MutableLiveData<>();
    public LiveData<Resource<List<Customer>>> getCustomerListState() { return customerListState; }

    private MutableLiveData<Resource<String>> actionState = new MutableLiveData<>();
    public LiveData<Resource<String>> getActionState() { return actionState; }

    public void fetchCustomers() {
        customerListState.setValue(Resource.loading());
        useCases.getCustomers.execute(resource -> customerListState.setValue(resource));
    }

    public void addCustomer(Customer customer) {
        actionState.setValue(Resource.loading());
        useCases.addCustomer.execute(customer, resource -> actionState.setValue(resource));
    }

    public void updateCustomer(Customer customer) {
        actionState.setValue(Resource.loading());
        useCases.updateCustomer.execute(customer, resource -> actionState.setValue(resource));
    }

    public void deleteCustomer(String customerId) {
        actionState.setValue(Resource.loading());
        useCases.deleteCustomer.execute(customerId, resource -> actionState.setValue(resource));
    }

    public void clearActionState() {
        actionState.setValue(null);
    }
}