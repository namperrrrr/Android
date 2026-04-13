package com.example.dienmayxanh.features.employees.data;


import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class Employee implements Serializable {
    @Exclude
    private String id;
    private String name;
    private String email;
    private String phone;

    // Lưu ID của chức danh (thay vì lưu cứng tên phòng ban như cũ)
    private String roleId;

    // Trạng thái: true = Đang làm việc, false = Đã nghỉ việc/Bị khóa
    private boolean active;

    // Constructor rỗng (BẮT BUỘC PHẢI CÓ để Firebase parse được dữ liệu về object)
    public Employee() {
    }

    // Constructor đầy đủ
    public Employee(String id, String name, String email, String phone, String roleId, boolean active) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.roleId = roleId;
        this.active = active;
    }

    // --- GETTER & SETTER ---

    @Exclude
    public String getId() {
        return id;
    }

    @Exclude
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}