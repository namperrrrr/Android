package com.example.dienmayxanh.features.employees.domain;

import com.example.dienmayxanh.features.employees.data.Employee;
import com.example.dienmayxanh.features.employees.data.EmployeeRepository;

import java.util.List;

public class GetEmployeesUseCase {
    private EmployeeRepository repository;
    public GetEmployeesUseCase() {
        this.repository = new EmployeeRepository();
    }
    public void execute(EmployeeRepository.EmployeeCallback<List<Employee>> callback) {
        repository.getEmployees(callback);
    }
}
