package com.example.dienmayxanh.features.employees.domain;

import com.example.dienmayxanh.features.employees.data.Employee;
import com.example.dienmayxanh.features.employees.data.EmployeeRepository;

public class UpdateEmployeeUseCase {
    private EmployeeRepository repository = new EmployeeRepository();

    public void execute(Employee Employee, EmployeeRepository.EmployeeCallback<String> callback) {
        repository.updateEmployee(Employee, callback);
    }
}