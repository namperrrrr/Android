package com.example.dienmayxanh.features.products.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dienmayxanh.core.network.Resource;
import com.example.dienmayxanh.features.products.data.Product;
import com.example.dienmayxanh.features.products.domain.ProductUseCases;
import java.util.List;

import com.example.dienmayxanh.features.categories.data.Category;
import com.example.dienmayxanh.features.categories.domain.CategoryUseCases;
import com.example.dienmayxanh.features.suppliers.data.Supplier;
import com.example.dienmayxanh.features.suppliers.domain.SupplierUseCases;

public class ProductViewModel extends ViewModel {
    private ProductUseCases useCases = new ProductUseCases();
    private CategoryUseCases categoryUseCases = new CategoryUseCases();
    private SupplierUseCases supplierUseCases = new SupplierUseCases();

    private final MutableLiveData<Resource<Void>> deleteProductState = new MutableLiveData<>();
    private MutableLiveData<Resource<List<Product>>> productListState = new MutableLiveData<>();
    public LiveData<Resource<List<Product>>> getProductListState() { return productListState; }
    public LiveData<Resource<Void>> getDeleteProductState() {
        return deleteProductState;
    }

    private MutableLiveData<Resource<List<Category>>> categoriesState = new MutableLiveData<>();
    public LiveData<Resource<List<Category>>> getCategoriesState() { return categoriesState; }

    private MutableLiveData<Resource<List<Supplier>>> suppliersState = new MutableLiveData<>();
    public LiveData<Resource<List<Supplier>>> getSuppliersState() { return suppliersState; }

    private MutableLiveData<Resource<String>> actionState = new MutableLiveData<>();
    public LiveData<Resource<String>> getActionState() { return actionState; }

    // Hàm gọi data
    public void fetchCategoriesForDropdown() {
        categoriesState.setValue(Resource.<List<Category>>loading());
        categoryUseCases.getCategories(resource -> categoriesState.setValue(resource));
    }

    public void fetchSuppliersForDropdown() {
        suppliersState.setValue(Resource.<List<Supplier>>loading());
        supplierUseCases.getSuppliers(resource -> suppliersState.setValue(resource));
    }

    public void fetchProducts() {
        productListState.setValue(Resource.loading());
        // Đã sửa: thêm .execute
        useCases.getProducts.execute(resource -> productListState.setValue(resource));
    }

    public void addProduct(Product product) {
        actionState.setValue(Resource.loading());
        // Đã sửa: thêm .execute
        useCases.addProduct.execute(product, resource -> actionState.setValue(resource));
    }

    public void updateProduct(Product product) {
        actionState.setValue(Resource.loading());
        // Đã sửa: thêm .execute
        useCases.updateProduct.execute(product, resource -> actionState.setValue(resource));
    }

    public void deleteProduct(String productId) {
        actionState.setValue(Resource.loading());
        // Đã sửa: thêm .execute
        useCases.deleteProduct.execute(productId, resource -> actionState.setValue(resource));
    }

    public void clearActionState() {
        actionState.setValue(null);
    }
}