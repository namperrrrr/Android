package com.example.dienmayxanh.features.suppliers.domain;

import com.example.dienmayxanh.features.suppliers.data.Supplier_Repository;

public class Delete_Supplier_UseCase {
    private Supplier_Repository repository = new Supplier_Repository();

    public void execute(String supplierId, Supplier_Repository.SupplierCallback<Void> callback) {
        repository.deleteSupplier(supplierId, callback);
    }
}