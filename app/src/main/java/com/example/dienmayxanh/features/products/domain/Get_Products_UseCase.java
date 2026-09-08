package com.example.dienmayxanh.features.products.domain;

import com.example.dienmayxanh.features.products.data.Product;
import com.example.dienmayxanh.features.products.data.Product_Repository;

import java.util.List;

public class Get_Products_UseCase {
    private Product_Repository repository;

    public Get_Products_UseCase() {
        this.repository = new Product_Repository();
    }

    public void execute(Product_Repository.ProductCallback<List<Product>> callback) {
        repository.getProducts(callback);
    }
}