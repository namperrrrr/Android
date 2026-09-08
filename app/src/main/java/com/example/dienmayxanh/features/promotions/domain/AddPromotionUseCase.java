package com.example.dienmayxanh.features.promotions.domain;

import com.example.dienmayxanh.features.promotions.data.Promotion;
import com.example.dienmayxanh.features.promotions.data.PromotionRepository;

public class AddPromotionUseCase {
    private PromotionRepository repository = new PromotionRepository();

    public void execute(Promotion promotion, PromotionRepository.PromotionCallback<String> callback) {
        repository.addPromotion(promotion, callback);
    }
}