package com.example.dienmayxanh.features.auth.data;

public class User {
    private String displayName;
    private String email;
    private String role;
    private String uid;

    // Constructor rỗng (Bắt buộc cho Firebase)
    public User() {}

    public User(String displayName, String email, String role, String uid) {
        this.displayName = displayName;
        this.email = email;
        this.role = role;
        this.uid = uid;
    }

    // --- GETTERS ---
    public String getUid() { return uid; }
    public String getDisplayName() { return displayName; }
    public String getEmail() { return email; }
    public String getRole() { return role; }

    // --- SETTERS (BẮT BUỘC THÊM) ---
    public void setUid(String uid) { this.uid = uid; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }
    public void setEmail(String email) { this.email = email; }
    public void setRole(String role) { this.role = role; }
}