package com.example.dienmayxanh.features.categories.data;

import com.google.firebase.firestore.Exclude;
import java.io.Serializable;

public class Category implements Serializable {
    @Exclude
    private String id;
    private String name;
    private String description;
    private boolean active;

    public Category() {} // Bắt buộc cho Firebase

    public Category(String name, String description, boolean active) {
        this.name = name;
        this.description = description;
        this.active = active;
    }

    @Exclude
    public String getId() { return id; }
    @Exclude
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}