package com.example.dienmayxanh.features.products.data;

import com.example.dienmayxanh.core.network.Resource;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String COLLECTION_NAME = "products";

    public interface ProductCallback<T> {
        void onComplete(Resource<T> resource);
    }

    public void getProducts(ProductCallback<List<Product>> callback) {
        db.collection(COLLECTION_NAME).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Product> products = new ArrayList<>();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Product product = document.toObject(Product.class);
                        product.setId(document.getId());
                        products.add(product);
                    }
                    callback.onComplete(Resource.success(products));
                })
                .addOnFailureListener(e -> callback.onComplete(Resource.error(e.getMessage())));
    }

    public void addProduct(Product product, ProductCallback<String> callback) {
        // Tự động sinh ID, đảm bảo người dùng không can thiệp nhập tay ID
        db.collection(COLLECTION_NAME).document().set(product)
                .addOnSuccessListener(aVoid -> callback.onComplete(Resource.success("Thêm sản phẩm thành công!")))
                .addOnFailureListener(e -> callback.onComplete(Resource.error(e.getMessage())));
    }

    public void updateProduct(Product product, ProductCallback<String> callback) {
        if (product.getId() == null) {
            callback.onComplete(Resource.error("Không tìm thấy ID sản phẩm!"));
            return;
        }
        db.collection(COLLECTION_NAME).document(product.getId()).set(product)
                .addOnSuccessListener(aVoid -> callback.onComplete(Resource.success("Cập nhật thành công!")))
                .addOnFailureListener(e -> callback.onComplete(Resource.error(e.getMessage())));
    }

    public void deleteProduct(String productId, ProductCallback<String> callback) {
        db.collection(COLLECTION_NAME).document(productId).delete()
                .addOnSuccessListener(aVoid -> callback.onComplete(Resource.success("Đã xóa sản phẩm!")))
                .addOnFailureListener(e -> callback.onComplete(Resource.error(e.getMessage())));
    }
}