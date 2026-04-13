package com.example.dienmayxanh.features.products.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dienmayxanh.core.network.Resource;
import com.example.dienmayxanh.features.products.data.Product;
import com.example.dienmayxanh.features.products.domain.ProductUseCases;
import java.util.List;

public class ProductViewModel extends ViewModel {
    private ProductUseCases useCases = new ProductUseCases();

    private final MutableLiveData<Resource<Void>> deleteProductState = new MutableLiveData<>();
    private MutableLiveData<Resource<List<Product>>> productListState = new MutableLiveData<>();
    public LiveData<Resource<List<Product>>> getProductListState() { return productListState; }
    public LiveData<Resource<Void>> getDeleteProductState() {
        return deleteProductState;
    }

    private MutableLiveData<Resource<String>> actionState = new MutableLiveData<>();
    public LiveData<Resource<String>> getActionState() { return actionState; }

    public void fetchProducts() {
        productListState.setValue(Resource.loading());
        useCases.getProducts(resource -> productListState.setValue(resource));
    }

    public void addProduct(Product product) {
        actionState.setValue(Resource.loading());
        useCases.addProduct(product, resource -> actionState.setValue(resource));
    }

    public void updateProduct(Product product) {
        actionState.setValue(Resource.loading());
        useCases.updateProduct(product, resource -> actionState.setValue(resource));
    }

    public void deleteProduct(String productId) {
        actionState.setValue(Resource.loading());
        useCases.deleteProduct(productId, resource -> actionState.setValue(resource));
    }

    public void clearActionState() {
        actionState.setValue(null);
    }
}