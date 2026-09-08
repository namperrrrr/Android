package com.example.dienmayxanh.features.promotions.domain;

import com.example.dienmayxanh.features.promotions.data.Promotion;
import com.example.dienmayxanh.features.promotions.data.PromotionRepository;
import java.util.List;

public class GetPromotionsUseCase {
    private PromotionRepository repository = new PromotionRepository();

    public void execute(PromotionRepository.PromotionCallback<List<Promotion>> callback) {
        repository.getPromotions(callback);
    }
}