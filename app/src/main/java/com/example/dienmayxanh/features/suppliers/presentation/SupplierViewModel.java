package com.example.dienmayxanh.features.suppliers.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.dienmayxanh.core.network.Resource;
import com.example.dienmayxanh.features.suppliers.data.Supplier;
import com.example.dienmayxanh.features.suppliers.domain.SupplierUseCases;
import java.util.List;

public class SupplierViewModel extends ViewModel {
    private SupplierUseCases useCases = new SupplierUseCases();

    private final MutableLiveData<Resource<Void>> deleteSupplierState = new MutableLiveData<>();
    private MutableLiveData<Resource<List<Supplier>>> supplierListState = new MutableLiveData<>();
    private MutableLiveData<Resource<String>> actionState = new MutableLiveData<>();

    public LiveData<Resource<List<Supplier>>> getSupplierListState() { return supplierListState; }
    public LiveData<Resource<Void>> getDeleteSupplierState() { return deleteSupplierState; }
    public LiveData<Resource<String>> getActionState() { return actionState; }

    public void fetchSuppliers() {
        supplierListState.setValue(Resource.<List<Supplier>>loading());
        useCases.getSuppliers(resource -> supplierListState.setValue(resource));
    }

    public void addSupplier(Supplier supplier) {
        actionState.setValue(Resource.<String>loading());
        useCases.addSupplier(supplier, resource -> actionState.setValue(resource));
    }

    public void updateSupplier(Supplier supplier) {
        actionState.setValue(Resource.<String>loading());
        useCases.updateSupplier(supplier, resource -> actionState.setValue(resource));
    }

    public void deleteSupplier(String supplierId) {
        deleteSupplierState.setValue(Resource.<Void>loading());
        useCases.deleteSupplier(supplierId, resource -> deleteSupplierState.setValue(resource));
    }

    public void clearActionState() { actionState.setValue(null); }
}