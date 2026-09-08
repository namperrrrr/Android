package com.example.dienmayxanh.features.orders.data;

import com.example.dienmayxanh.core.network.Resource;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class OrderRepository {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String COLLECTION_NAME = "orders"; // Tự động tạo bảng "orders" trên Firebase

    public interface OrderCallback<T> {
        void onComplete(Resource<T> resource);
    }

    public void getOrders(OrderCallback<List<Order>> callback) {
        db.collection(COLLECTION_NAME).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Order> orders = new ArrayList<>();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Order order = document.toObject(Order.class);
                        order.setId(document.getId());
                        orders.add(order);
                    }
                    callback.onComplete(Resource.success(orders));
                })
                .addOnFailureListener(e -> callback.onComplete(Resource.error(e.getMessage())));
    }

    public void addOrder(Order order, OrderCallback<String> callback) {
        db.collection(COLLECTION_NAME).document().set(order)
                .addOnSuccessListener(aVoid -> callback.onComplete(Resource.success("Thêm giao dịch thành công!")))
                .addOnFailureListener(e -> callback.onComplete(Resource.error(e.getMessage())));
    }

    public void updateOrder(Order order, OrderCallback<String> callback) {
        if (order.getId() == null) {
            callback.onComplete(Resource.error("Không tìm thấy ID giao dịch!"));
            return;
        }
        db.collection(COLLECTION_NAME).document(order.getId()).set(order)
                .addOnSuccessListener(aVoid -> callback.onComplete(Resource.success("Cập nhật thành công!")))
                .addOnFailureListener(e -> callback.onComplete(Resource.error(e.getMessage())));
    }

    public void deleteOrder(String orderId, OrderCallback<String> callback) {
        db.collection(COLLECTION_NAME).document(orderId).delete()
                .addOnSuccessListener(aVoid -> callback.onComplete(Resource.success("Đã xóa giao dịch!")))
                .addOnFailureListener(e -> callback.onComplete(Resource.error(e.getMessage())));
    }
}