package com.example.dienmayxanh.features.orders.data;

import com.google.firebase.firestore.Exclude;
import java.io.Serializable;

public class Order implements Serializable {
    @Exclude
    private String id;
    private String customerName;
    private String items;
    private String total;
    private String status;
    private String voucher;

    public Order() {}

    public Order(String customerName, String items, String total, String status, String voucher) {
        this.customerName = customerName;
        this.items = items;
        this.total = total;
        this.status = status;
        this.voucher = voucher;
    }

    @Exclude
    public String getId() { return id; }
    @Exclude
    public void setId(String id) { this.id = id; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getItems() { return items; }
    public void setItems(String items) { this.items = items; }

    public String getTotal() { return total; }
    public void setTotal(String total) { this.total = total; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getVoucher() { return voucher; }
    public void setVoucher(String voucher) { this.voucher = voucher; }
}