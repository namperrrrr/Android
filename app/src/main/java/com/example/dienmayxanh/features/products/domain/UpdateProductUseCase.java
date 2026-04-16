package com.example.dienmayxanh.features.products.domain;

import com.example.dienmayxanh.features.products.data.Product;
import com.example.dienmayxanh.features.products.data.ProductRepository;

public class UpdateProductUseCase {
    private ProductRepository repository = new ProductRepository();

    public void execute(Product product, ProductRepository.ProductCallback<String> callback) {
        repository.updateProduct(product, callback);
    }
}