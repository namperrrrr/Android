package com.example.dienmayxanh.features.roles.data;

import com.google.firebase.firestore.Exclude;
import java.io.Serializable;

public class Role implements Serializable {
    @Exclude
    private String id;
    private String name;        // Ví dụ: "Quản lý", "Nhân viên Bán hàng"
    private String description; // Ví dụ: "Quản lý toàn bộ hoạt động cửa hàng"

    // Bắt buộc phải có Constructor rỗng cho Firebase
    public Role() {
    }

    public Role(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}