package com.example.dienmayxanh.features.suppliers.domain;

import com.example.dienmayxanh.features.suppliers.data.Supplier;
import com.example.dienmayxanh.features.suppliers.data.Supplier_Repository;

public class Add_Supplier_UseCase {
    private Supplier_Repository repository = new Supplier_Repository();

    public void execute(Supplier supplier, Supplier_Repository.SupplierCallback<String> callback) {
        repository.addSupplier(supplier, callback);
    }
}