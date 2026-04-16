package com.example.dienmayxanh.features.suppliers.domain;

import com.example.dienmayxanh.features.suppliers.data.Supplier;
import com.example.dienmayxanh.features.suppliers.data.SupplierRepository;

import java.util.List;

public class GetSuppliersUseCase {
    private SupplierRepository repository;

    public GetSuppliersUseCase() {
        this.repository = new SupplierRepository();
    }

    public void execute(SupplierRepository.SupplierCallback<List<Supplier>> callback) {
        repository.getSuppliers(callback);
    }
}