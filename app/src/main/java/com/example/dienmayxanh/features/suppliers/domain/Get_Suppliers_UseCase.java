package com.example.dienmayxanh.features.suppliers.domain;

import com.example.dienmayxanh.features.suppliers.data.Supplier;
import com.example.dienmayxanh.features.suppliers.data.Supplier_Repository;

import java.util.List;

public class Get_Suppliers_UseCase {
    private Supplier_Repository repository;

    public Get_Suppliers_UseCase() {
        this.repository = new Supplier_Repository();
    }

    public void execute(Supplier_Repository.SupplierCallback<List<Supplier>> callback) {
        repository.getSuppliers(callback);
    }
}