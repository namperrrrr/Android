package com.example.dienmayxanh.features.employees.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.dienmayxanh.R;
import com.example.dienmayxanh.features.employees.data.Employee;

public class AddEditEmployeeFragment extends Fragment {

    private TextView tvFormTitle;
    private EditText edtEmployeeName, edtEmployeePhone, edtEmployeeEmail, edtEmployeeRole;
    private Switch switchIsActive;
    private Button btnBack, btnSaveEmployee;

    // Gắn ViewModel vào để thao tác với Database
    private EmployeeViewModel viewModel;

    private boolean isAddMode = true;
    private String currentEmployeeId = null; // Lưu ID nếu đang ở chế độ Sửa

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_edit_employee, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        setupViewModel();
        checkModeAndLoadData(); // Kiểm tra xem là Thêm hay Sửa để tải dữ liệu
        setupEvents();
    }

    private void initViews(View view) {
        tvFormTitle = view.findViewById(R.id.tvFormTitle);
        edtEmployeeName = view.findViewById(R.id.edtEmployeeName);
        edtEmployeePhone = view.findViewById(R.id.edtEmployeePhone);
        edtEmployeeEmail = view.findViewById(R.id.edtEmployeeEmail);
        edtEmployeeRole = view.findViewById(R.id.edtEmployeeRole);
        switchIsActive = view.findViewById(R.id.switchIsActive);
        btnBack = view.findViewById(R.id.btnBack);
        btnSaveEmployee = view.findViewById(R.id.btnSaveEmployee);
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(EmployeeViewModel.class);

        // Lắng nghe kết quả từ Firebase (Lưu thành công hay thất bại)
        viewModel.getActionState().observe(getViewLifecycleOwner(), resource -> {
            if (resource != null) {
                switch (resource.status) {
                    case LOADING:
                        btnSaveEmployee.setEnabled(false);
                        btnSaveEmployee.setText("ĐANG LƯU...");
                        break;
                    case SUCCESS:
                        Toast.makeText(getContext(), "Lưu thông tin thành công!", Toast.LENGTH_SHORT).show();
                        viewModel.clearActionState(); // Dọn dẹp trạng thái

                        // Cập nhật xong thì tự động quay về màn hình danh sách
                        requireActivity().getSupportFragmentManager().popBackStack();
                        break;
                    case ERROR:
                        btnSaveEmployee.setEnabled(true);
                        btnSaveEmployee.setText(isAddMode ? "TẠO NHÂN VIÊN" : "LƯU THAY ĐỔI");
                        Toast.makeText(getContext(), "Lỗi: " + resource.message, Toast.LENGTH_LONG).show();
                        viewModel.clearActionState();
                        break;
                }
            }
        });
    }

    private void checkModeAndLoadData() {
        // Mở gói hàng (Bundle) gửi từ màn hình danh sách sang
        Bundle args = getArguments();

        // Nếu có gói hàng tên là "EMPLOYEE_DATA" -> Chắc chắn đây là chế độ SỬA
        if (args != null && args.containsKey("EMPLOYEE_DATA")) {
            isAddMode = false;
            Employee employeeToEdit = (Employee) args.getSerializable("EMPLOYEE_DATA");

            if (employeeToEdit != null) {
                // Lưu lại ID để tí nữa Firebase biết đường update đúng người
                currentEmployeeId = employeeToEdit.getId();

                // Điền thông tin cũ vào các ô nhập liệu
                tvFormTitle.setText("CẬP NHẬT THÔNG TIN");
                btnSaveEmployee.setText("LƯU THAY ĐỔI");

                edtEmployeeName.setText(employeeToEdit.getName());
                edtEmployeePhone.setText(employeeToEdit.getPhone());
                edtEmployeeEmail.setText(employeeToEdit.getEmail());
                edtEmployeeRole.setText(employeeToEdit.getRoleId());
                switchIsActive.setChecked(employeeToEdit.isActive());

                // Cập nhật chữ của Switch cho khớp trạng thái
                switchIsActive.setText(employeeToEdit.isActive() ? "Đang làm việc " : "Đã nghỉ / Khóa ");
            }
        } else {
            // Không có gói hàng -> Chế độ THÊM MỚI
            isAddMode = true;
            tvFormTitle.setText("THÊM NHÂN VIÊN MỚI");
            btnSaveEmployee.setText("TẠO NHÂN VIÊN");
        }
    }

    private void setupEvents() {
        switchIsActive.setOnCheckedChangeListener((buttonView, isChecked) -> {
            switchIsActive.setText(isChecked ? "Đang làm việc " : "Đã nghỉ / Khóa ");
        });

        btnBack.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        btnSaveEmployee.setOnClickListener(v -> saveEmployeeData());
    }

    private void saveEmployeeData() {
        String name = edtEmployeeName.getText().toString().trim();
        String phone = edtEmployeePhone.getText().toString().trim();
        String email = edtEmployeeEmail.getText().toString().trim();
        String roleId = edtEmployeeRole.getText().toString().trim();
        boolean isActive = switchIsActive.isChecked();

        // Validate dữ liệu cơ bản
        if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || roleId.isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        // Đóng gói dữ liệu mới vào 1 object Employee
        Employee employee = new Employee();
        employee.setName(name);
        employee.setPhone(phone);
        employee.setEmail(email);
        employee.setRoleId(roleId);
        employee.setActive(isActive);

        // Phân luồng: Gọi ViewModel để đẩy lên Firebase
        if (isAddMode) {
            viewModel.addEmployee(employee);
        } else {
            employee.setId(currentEmployeeId); // Gắn ID cũ vào để Firebase biết đường đè data lên
            viewModel.updateEmployee(employee);
        }
    }
}