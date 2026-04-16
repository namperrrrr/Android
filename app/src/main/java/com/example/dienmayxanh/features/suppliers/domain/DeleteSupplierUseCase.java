package com.example.dienmayxanh.features.suppliers.domain;

import com.example.dienmayxanh.features.suppliers.data.SupplierRepository;

public class DeleteSupplierUseCase {
    private SupplierRepository repository = new SupplierRepository();

    public void execute(String supplierId, SupplierRepository.SupplierCallback<Void> callback) {
        repository.deleteSupplier(supplierId, callback);
    }
}