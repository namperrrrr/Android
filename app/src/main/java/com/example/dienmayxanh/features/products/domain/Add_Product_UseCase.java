package com.example.dienmayxanh.features.products.domain;

import com.example.dienmayxanh.features.products.data.Product;
import com.example.dienmayxanh.features.products.data.Product_Repository;

public class Add_Product_UseCase {
    private Product_Repository repository = new Product_Repository();

    public void execute(Product product, Product_Repository.ProductCallback<String> callback) {
        repository.addProduct(product, callback);
    }
}