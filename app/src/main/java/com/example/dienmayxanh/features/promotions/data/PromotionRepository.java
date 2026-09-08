package com.example.dienmayxanh.features.promotions.data;

import com.example.dienmayxanh.core.network.Resource;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class PromotionRepository {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String COLLECTION_NAME = "promotions"; // Tự động tạo bảng trên Firebase

    public interface PromotionCallback<T> {
        void onComplete(Resource<T> resource);
    }

    public void getPromotions(PromotionCallback<List<Promotion>> callback) {
        db.collection(COLLECTION_NAME).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Promotion> promotions = new ArrayList<>();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Promotion promotion = document.toObject(Promotion.class);
                        promotion.setId(document.getId());
                        promotions.add(promotion);
                    }
                    callback.onComplete(Resource.success(promotions));
                })
                .addOnFailureListener(e -> callback.onComplete(Resource.error(e.getMessage())));
    }

    public void addPromotion(Promotion promotion, PromotionCallback<String> callback) {
        db.collection(COLLECTION_NAME).document().set(promotion)
                .addOnSuccessListener(aVoid -> callback.onComplete(Resource.success("Thêm mã khuyến mãi thành công!")))
                .addOnFailureListener(e -> callback.onComplete(Resource.error(e.getMessage())));
    }

    public void updatePromotion(Promotion promotion, PromotionCallback<String> callback) {
        if (promotion.getId() == null) {
            callback.onComplete(Resource.error("Lỗi: Không tìm thấy ID!"));
            return;
        }
        db.collection(COLLECTION_NAME).document(promotion.getId()).set(promotion)
                .addOnSuccessListener(aVoid -> callback.onComplete(Resource.success("Cập nhật thành công!")))
                .addOnFailureListener(e -> callback.onComplete(Resource.error(e.getMessage())));
    }

    public void deletePromotion(String promotionId, PromotionCallback<String> callback) {
        db.collection(COLLECTION_NAME).document(promotionId).delete()
                .addOnSuccessListener(aVoid -> callback.onComplete(Resource.success("Đã xóa mã khuyến mãi!")))
                .addOnFailureListener(e -> callback.onComplete(Resource.error(e.getMessage())));
    }
}