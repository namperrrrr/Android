package com.example.dienmayxanh.features.products.domain;

import com.example.dienmayxanh.features.products.data.ProductRepository;

public class DeleteProductUseCase {
    private ProductRepository repository = new ProductRepository();

    public void execute(String productId, ProductRepository.ProductCallback<String> callback) {
        repository.deleteProduct(productId, callback);
    }
}