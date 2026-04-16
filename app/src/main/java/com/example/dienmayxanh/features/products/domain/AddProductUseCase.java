package com.example.dienmayxanh.features.products.domain;

import com.example.dienmayxanh.features.products.data.Product;
import com.example.dienmayxanh.features.products.data.ProductRepository;

public class AddProductUseCase {
    private ProductRepository repository = new ProductRepository();

    public void execute(Product product, ProductRepository.ProductCallback<String> callback) {
        // Có thể thêm Validate (kiểm tra rỗng) ở đây trước khi gọi Repository
        repository.addProduct(product, callback);
    }
}