package com.example.dienmayxanh.features.employees.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dienmayxanh.core.network.Resource;
import com.example.dienmayxanh.features.employees.data.Employee;
import com.example.dienmayxanh.features.employees.domain.EmployeeUseCases;

import java.util.List;

public class EmployeeViewModel extends ViewModel {

    // Gọi giỏ UseCases đã gom sẵn
    private EmployeeUseCases useCases = new EmployeeUseCases();

    // 1. LiveData để chứa danh sách nhân viên đổ ra RecyclerView
    private MutableLiveData<Resource<List<Employee>>> employeeListState = new MutableLiveData<>();
    public LiveData<Resource<List<Employee>>> getEmployeeListState() { return employeeListState; }

    // 2. LiveData để chứa trạng thái của các hành động (Thêm, Sửa, Xóa)
    private MutableLiveData<Resource<String>> actionState = new MutableLiveData<>();
    public LiveData<Resource<String>> getActionState() { return actionState; }

    // --- CÁC HÀM GỌI USECASE ---

    // Lấy danh sách nhân viên
    public void fetchEmployees() {
        employeeListState.setValue(Resource.loading());
        useCases.getEmployees.execute(resource -> employeeListState.setValue(resource));
    }

    // Thêm nhân viên
    public void addEmployee(Employee employee) {
        actionState.setValue(Resource.loading());
        useCases.addEmployee.execute(employee, resource -> actionState.setValue(resource));
    }

    // Cập nhật thông tin nhân viên
    public void updateEmployee(Employee employee) {
        actionState.setValue(Resource.loading());
        useCases.updateEmployee.execute(employee, resource -> actionState.setValue(resource));
    }

    // Xóa nhân viên
    public void deleteEmployee(String employeeId) {
        actionState.setValue(Resource.loading());
        useCases.deleteEmployee.execute(employeeId, resource -> actionState.setValue(resource));
    }

    // --- HÀM TIỆN ÍCH ---

    // Dọn dẹp trạng thái hành động (tránh lỗi nhảy Fragment hoặc hiện Toast 2 lần)
    public void clearActionState() {
        actionState.setValue(null);
    }
}