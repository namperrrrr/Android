package com.example.dienmayxanh.features.customers.data;

import com.example.dienmayxanh.core.network.Resource;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class CustomerRepository {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String COLLECTION_NAME = "customers";

    public interface CustomerCallback<T> {
        void onComplete(Resource<T> resource);
    }

    public void getCustomers(CustomerCallback<List<Customer>> callback) {
        db.collection(COLLECTION_NAME).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Customer> customers = new ArrayList<>();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Customer customer = document.toObject(Customer.class);
                        customer.setId(document.getId());
                        customers.add(customer);
                    }
                    callback.onComplete(Resource.success(customers));
                })
                .addOnFailureListener(e -> callback.onComplete(Resource.error(e.getMessage())));
    }

    public void addCustomer(Customer customer, CustomerCallback<String> callback) {
        db.collection(COLLECTION_NAME).document().set(customer)
                .addOnSuccessListener(aVoid -> callback.onComplete(Resource.success("Thêm khách hàng thành công!")))
                .addOnFailureListener(e -> callback.onComplete(Resource.error(e.getMessage())));
    }

    public void updateCustomer(Customer customer, CustomerCallback<String> callback) {
        if (customer.getId() == null) {
            callback.onComplete(Resource.error("Lỗi: Không tìm thấy ID!"));
            return;
        }
        db.collection(COLLECTION_NAME).document(customer.getId()).set(customer)
                .addOnSuccessListener(aVoid -> callback.onComplete(Resource.success("Cập nhật thành công!")))
                .addOnFailureListener(e -> callback.onComplete(Resource.error(e.getMessage())));
    }

    public void deleteCustomer(String customerId, CustomerCallback<String> callback) {
        db.collection(COLLECTION_NAME).document(customerId).delete()
                .addOnSuccessListener(aVoid -> callback.onComplete(Resource.success("Đã xóa khách hàng!")))
                .addOnFailureListener(e -> callback.onComplete(Resource.error(e.getMessage())));
    }
}