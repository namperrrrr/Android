package com.example.dienmayxanh.features.customers.data;

import com.google.firebase.firestore.Exclude;
import java.io.Serializable;

public class Customer implements Serializable {
    @Exclude
    private String id;
    private String name;
    private String phone;
    private String address;

    // Firebase bắt buộc phải có constructor rỗng
    public Customer() {}

    public Customer(String name, String phone, String address) {
        this.name = name;
        this.phone = phone;
        this.address = address;
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
}