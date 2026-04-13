package com.example.dienmayxanh.features.employees.domain;

import com.example.dienmayxanh.features.employees.data.EmployeeRepository;

public class DeleteEmployeeUseCase {
    private EmployeeRepository repository = new EmployeeRepository();

    public void execute(String employeeId, EmployeeRepository.EmployeeCallback<String> callback) {
        repository.deleteEmployee(employeeId, callback);
    }
}