package com.example.dienmayxanh.features.suppliers.domain;

import com.example.dienmayxanh.features.suppliers.data.Supplier;
import com.example.dienmayxanh.features.suppliers.data.SupplierRepository;

public class UpdateSupplierUseCase {
    private SupplierRepository repository = new SupplierRepository();

    public void execute(Supplier supplier, SupplierRepository.SupplierCallback<String> callback) {
        repository.updateSupplier(supplier, callback);
    }
}