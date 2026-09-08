package com.example.dienmayxanh.features.products.domain;

import com.example.dienmayxanh.features.products.data.Product_Repository;

public class Delete_Product_UseCase {
    private Product_Repository repository = new Product_Repository();

    public void execute(String productId, Product_Repository.ProductCallback<String> callback) {
        repository.deleteProduct(productId, callback);
    }
}