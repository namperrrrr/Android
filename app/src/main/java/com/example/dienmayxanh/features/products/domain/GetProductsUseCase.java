package com.example.dienmayxanh.features.products.domain;

import com.example.dienmayxanh.features.products.data.Product;
import com.example.dienmayxanh.features.products.data.ProductRepository;

import java.util.List;

public class GetProductsUseCase {
    private ProductRepository repository;

    public GetProductsUseCase() {
        this.repository = new ProductRepository();
    }

    public void execute(ProductRepository.ProductCallback<List<Product>> callback) {
        repository.getProducts(callback);
    }
}