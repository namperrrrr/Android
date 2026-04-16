package com.example.dienmayxanh.features.suppliers.domain;

import com.example.dienmayxanh.features.suppliers.data.Supplier;
import com.example.dienmayxanh.features.suppliers.data.SupplierRepository;

public class AddSupplierUseCase {
    private SupplierRepository repository = new SupplierRepository();

    public void execute(Supplier supplier, SupplierRepository.SupplierCallback<String> callback) {
        repository.addSupplier(supplier, callback);
    }
}