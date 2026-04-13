package com.example.dienmayxanh.features.employees.domain;

public class EmployeeUseCases {
    public GetEmployeesUseCase getEmployees = new GetEmployeesUseCase();
    public AddEmployeeUseCase addEmployee = new AddEmployeeUseCase();
    public UpdateEmployeeUseCase updateEmployee = new UpdateEmployeeUseCase();
    public DeleteEmployeeUseCase deleteEmployee = new DeleteEmployeeUseCase();
}