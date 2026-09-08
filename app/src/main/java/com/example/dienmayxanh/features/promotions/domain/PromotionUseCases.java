package com.example.dienmayxanh.features.promotions.domain;

public class PromotionUseCases {
    public GetPromotionsUseCase getPromotions = new GetPromotionsUseCase();
    public AddPromotionUseCase addPromotion = new AddPromotionUseCase();
    public UpdatePromotionUseCase updatePromotion = new UpdatePromotionUseCase();
    public DeletePromotionUseCase deletePromotion = new DeletePromotionUseCase();
}