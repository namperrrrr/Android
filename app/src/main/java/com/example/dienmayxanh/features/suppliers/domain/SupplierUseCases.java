package com.example.dienmayxanh.features.suppliers.domain;

import com.example.dienmayxanh.features.suppliers.data.Supplier;
import com.example.dienmayxanh.features.suppliers.data.SupplierRepository;
import java.util.List;

public class SupplierUseCases {
    private final SupplierRepository repository;

    public SupplierUseCases() {
        this.repository = new SupplierRepository();
    }

    public void getSuppliers(SupplierRepository.SupplierCallback<List<Supplier>> callback) {
        repository.getSuppliers(callback);
    }

    public void addSupplier(Supplier supplier, SupplierRepository.SupplierCallback<String> callback) {
        repository.addSupplier(supplier, callback);
    }

    public void updateSupplier(Supplier supplier, SupplierRepository.SupplierCallback<String> callback) {
        repository.updateSupplier(supplier, callback);
    }

    public void deleteSupplier(String supplierId, SupplierRepository.SupplierCallback<Void> callback) {
        repository.deleteSupplier(supplierId, callback);
    }
}