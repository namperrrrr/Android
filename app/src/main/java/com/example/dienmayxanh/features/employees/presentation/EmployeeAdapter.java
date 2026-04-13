package com.example.dienmayxanh.features.employees.presentation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dienmayxanh.R;
import com.example.dienmayxanh.features.employees.data.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {

    private List<Employee> employeeList = new ArrayList<>();
    private final OnEmployeeClickListener listener;

    // Interface để bắt sự kiện khi bấm nút Sửa hoặc Xóa trên từng dòng
    public interface OnEmployeeClickListener {
        void onEditClick(Employee employee);
        void onDeleteClick(Employee employee);
    }

    public EmployeeAdapter(OnEmployeeClickListener listener) {
        this.listener = listener;
    }

    // Hàm cập nhật danh sách khi có data mới từ ViewModel
    public void setEmployees(List<Employee> newEmployees) {
        this.employeeList.clear();
        if (newEmployees != null) {
            this.employeeList.addAll(newEmployees);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Gắn file giao diện item_employee.xml vào đây
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_employee, parent, false);
        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        Employee currentEmployee = employeeList.get(position);

        // Đổ dữ liệu vào các ô chữ
        holder.tvEmployeeName.setText(currentEmployee.getName());
        holder.tvEmployeeRole.setText("Mã CV: " + currentEmployee.getRoleId() + " - " + currentEmployee.getPhone());

        // Đổi màu icon đại diện nếu nhân viên đã nghỉ việc (Tùy chọn cho sinh động)
        if (currentEmployee.isActive()) {
            holder.imgAvatar.setColorFilter(android.graphics.Color.parseColor("#1976D2")); // Xanh
        } else {
            holder.imgAvatar.setColorFilter(android.graphics.Color.parseColor("#9E9E9E")); // Xám
        }

        // Bắt sự kiện bấm nút Sửa
        holder.imgEdit.setOnClickListener(v -> {
            if (listener != null) listener.onEditClick(currentEmployee);
        });

        // Bắt sự kiện bấm nút Xóa
        holder.imgDelete.setOnClickListener(v -> {
            if (listener != null) listener.onDeleteClick(currentEmployee);
        });
    }

    @Override
    public int getItemCount() {
        return employeeList != null ? employeeList.size() : 0;
    }

    // Lớp nội bộ để ánh xạ các View trong file item_employee.xml
    static class EmployeeViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAvatar, imgEdit, imgDelete;
        TextView tvEmployeeName, tvEmployeeRole;

        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            imgEdit = itemView.findViewById(R.id.imgEdit);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            tvEmployeeName = itemView.findViewById(R.id.tvEmployeeName);
            tvEmployeeRole = itemView.findViewById(R.id.tvEmployeeRole);
        }
    }
}