package com.example.dienmayxanh.features.employees.domain;

import com.example.dienmayxanh.features.employees.data.Employee;
import com.example.dienmayxanh.features.employees.data.EmployeeRepository;

public class AddEmployeeUseCase {
    private EmployeeRepository repository = new EmployeeRepository();

    public void execute(Employee Employee, EmployeeRepository.EmployeeCallback<String> callback) {
        // Có thể thêm Validate (kiểm tra rỗng) ở đây trước khi gọi Repository
        repository.addEmployee(Employee, callback);
    }
}