package com.example.dienmayxanh.features.products.data;

import com.google.firebase.firestore.Exclude;
import java.io.Serializable;

public class Product implements Serializable {
    @Exclude
    private String id; // ID ẩn không lưu trực tiếp vào field của document
    private String name;
    private long price;
    private int stock;
    private String imageUrl;
    private String categoryId;
    private String supplierId;
    private boolean active;

    public Product() {} // Bắt buộc cho Firebase

    public Product(String name, long price, int stock, String imageUrl, String categoryId, String supplierId, boolean active) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.imageUrl = imageUrl;
        this.categoryId = categoryId;
        this.supplierId = supplierId;
        this.active = active;
    }

    @Exclude
    public String getId() { return id; }
    @Exclude
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public long getPrice() { return price; }
    public void setPrice(long price) { this.price = price; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public String getCategoryId() { return categoryId; }
    public void setCategoryId(String categoryId) { this.categoryId = categoryId; }
    public String getSupplierId() { return supplierId; }
    public void setSupplierId(String supplierId) { this.supplierId = supplierId; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}