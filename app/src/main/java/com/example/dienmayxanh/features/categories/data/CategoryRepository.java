package com.example.dienmayxanh.features.categories.data;

import com.example.dienmayxanh.core.network.Resource;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class CategoryRepository {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final String COLLECTION_NAME = "categories";

    // Đẩy interface Callback về tầng Data để hứng dữ liệu
    public interface CategoryCallback<T> {
        void onResult(Resource<T> resource);
    }

    public void getCategories(CategoryCallback<List<Category>> callback) {
        db.collection(COLLECTION_NAME)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Category> list = new ArrayList<>();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Category cat = document.toObject(Category.class);
                        cat.setId(document.getId());
                        list.add(cat);
                    }
                    callback.onResult(Resource.success(list));
                })
                // FIX: Chỉ truyền 1 tham số e.getMessage() theo đúng chuẩn Resource.java của bạn
                .addOnFailureListener(e -> callback.onResult(Resource.error(e.getMessage())));
    }

    public void addCategory(Category category, CategoryCallback<String> callback) {
        db.collection(COLLECTION_NAME)
                .add(category)
                .addOnSuccessListener(documentReference -> callback.onResult(Resource.success("Thêm danh mục thành công")))
                // FIX LỖI 2 THAM SỐ
                .addOnFailureListener(e -> callback.onResult(Resource.error(e.getMessage())));
    }

    public void updateCategory(Category category, CategoryCallback<String> callback) {
        db.collection(COLLECTION_NAME).document(category.getId())
                .set(category)
                .addOnSuccessListener(aVoid -> callback.onResult(Resource.success("Cập nhật danh mục thành công")))
                // FIX LỖI 2 THAM SỐ
                .addOnFailureListener(e -> callback.onResult(Resource.error(e.getMessage())));
    }

    public void deleteCategory(String categoryId, CategoryCallback<Void> callback) {
        db.collection(COLLECTION_NAME).document(categoryId)
                .delete()
                .addOnSuccessListener(aVoid -> callback.onResult(Resource.success(null)))
                // FIX LỖI 2 THAM SỐ
                .addOnFailureListener(e -> callback.onResult(Resource.error(e.getMessage())));
    }
}