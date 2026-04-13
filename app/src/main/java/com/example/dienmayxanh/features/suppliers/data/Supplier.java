package com.example.dienmayxanh.features.suppliers.data;

import com.google.firebase.firestore.Exclude;
import java.io.Serializable;

public class Supplier implements Serializable {
    @Exclude
    private String id; // Ẩn ID, Firebase tự lo
    private String name;
    private String phone;
    private String address;
    private boolean active;

    public Supplier() {} // Bắt buộc cho Firebase

    public Supplier(String name, String phone, String address, boolean active) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.active = active;
    }

    @Exclude
    public String getId() { return id; }
    @Exclude
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    @Override
    public String toString() {
        return this.name; // Trả về tên nhà cung cấp để Spinner hiển thị
    }
}