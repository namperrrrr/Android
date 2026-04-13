package com.example.dienmayxanh.features.suppliers.data;

import com.example.dienmayxanh.core.network.Resource;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;

public class SupplierRepository {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final String COLLECTION_NAME = "suppliers";

    public interface SupplierCallback<T> {
        void onResult(Resource<T> resource);
    }

    public void getSuppliers(SupplierCallback<List<Supplier>> callback) {
        db.collection(COLLECTION_NAME).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Supplier> list = new ArrayList<>();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Supplier sup = document.toObject(Supplier.class);
                        sup.setId(document.getId());
                        list.add(sup);
                    }
                    callback.onResult(Resource.success(list));
                })
                .addOnFailureListener(e -> callback.onResult(Resource.error(e.getMessage())));
    }

    public void addSupplier(Supplier supplier, SupplierCallback<String> callback) {
        db.collection(COLLECTION_NAME).add(supplier)
                .addOnSuccessListener(docRef -> callback.onResult(Resource.success("Thêm nhà cung cấp thành công")))
                .addOnFailureListener(e -> callback.onResult(Resource.error(e.getMessage())));
    }

    public void updateSupplier(Supplier supplier, SupplierCallback<String> callback) {
        db.collection(COLLECTION_NAME).document(supplier.getId()).set(supplier)
                .addOnSuccessListener(aVoid -> callback.onResult(Resource.success("Cập nhật thành công")))
                .addOnFailureListener(e -> callback.onResult(Resource.error(e.getMessage())));
    }

    public void deleteSupplier(String supplierId, SupplierCallback<Void> callback) {
        db.collection(COLLECTION_NAME).document(supplierId).delete()
                .addOnSuccessListener(aVoid -> callback.onResult(Resource.success(null)))
                .addOnFailureListener(e -> callback.onResult(Resource.error(e.getMessage())));
    }
}