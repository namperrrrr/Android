package com.example.dienmayxanh.features.promotions.data;

import com.google.firebase.firestore.Exclude;
import java.io.Serializable;

public class Promotion implements Serializable {
    @Exclude
    private String id;
    private String code;        // Mã giảm giá (VD: SALE50K)
    private String discount;    // Mức giảm (VD: 50.000đ)
    private String description; // Mô tả điều kiện
    private String status;      // Trạng thái (VD: Còn hạn, Hết hạn)

    // Firebase bắt buộc phải có constructor rỗng
    public Promotion() {}

    public Promotion(String code, String discount, String description, String status) {
        this.code = code;
        this.discount = discount;
        this.description = description;
        this.status = status;
    }

    @Exclude
    public String getId() { return id; }
    @Exclude
    public void setId(String id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getDiscount() { return discount; }
    public void setDiscount(String discount) { this.discount = discount; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}