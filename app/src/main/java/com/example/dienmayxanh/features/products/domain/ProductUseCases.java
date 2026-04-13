package com.example.dienmayxanh.features.products.domain;
import com.example.dienmayxanh.features.products.data.Product;
import com.example.dienmayxanh.features.products.data.ProductRepository;
import java.util.List;

public class ProductUseCases {
    private ProductRepository repository = new ProductRepository();

    public void getProducts(ProductRepository.ProductCallback<List<Product>> callback) {
        repository.getProducts(callback);
    }
    public void addProduct(Product product, ProductRepository.ProductCallback<String> callback) {
        repository.addProduct(product, callback);
    }
    public void updateProduct(Product product, ProductRepository.ProductCallback<String> callback) {
        repository.updateProduct(product, callback);
    }
    public void deleteProduct(String productId, ProductRepository.ProductCallback<String> callback) {
        repository.deleteProduct(productId, callback);
    }
}