package com.example.dienmayxanh.features.promotions.domain;

import com.example.dienmayxanh.features.promotions.data.PromotionRepository;

public class DeletePromotionUseCase {
    private PromotionRepository repository = new PromotionRepository();

    public void execute(String promotionId, PromotionRepository.PromotionCallback<String> callback) {
        repository.deletePromotion(promotionId, callback);
    }
}