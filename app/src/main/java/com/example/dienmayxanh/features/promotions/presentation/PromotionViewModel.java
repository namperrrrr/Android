package com.example.dienmayxanh.features.promotions.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.dienmayxanh.core.network.Resource;
import com.example.dienmayxanh.features.promotions.data.Promotion;
import com.example.dienmayxanh.features.promotions.domain.PromotionUseCases;
import java.util.List;

public class PromotionViewModel extends ViewModel {
    private PromotionUseCases useCases = new PromotionUseCases();

    private MutableLiveData<Resource<List<Promotion>>> promotionListState = new MutableLiveData<>();
    public LiveData<Resource<List<Promotion>>> getPromotionListState() { return promotionListState; }

    private MutableLiveData<Resource<String>> actionState = new MutableLiveData<>();
    public LiveData<Resource<String>> getActionState() { return actionState; }

    public void fetchPromotions() {
        promotionListState.setValue(Resource.loading());
        useCases.getPromotions.execute(resource -> promotionListState.setValue(resource));
    }

    public void addPromotion(Promotion promotion) {
        actionState.setValue(Resource.loading());
        useCases.addPromotion.execute(promotion, resource -> actionState.setValue(resource));
    }

    public void updatePromotion(Promotion promotion) {
        actionState.setValue(Resource.loading());
        useCases.updatePromotion.execute(promotion, resource -> actionState.setValue(resource));
    }

    public void deletePromotion(String promotionId) {
        actionState.setValue(Resource.loading());
        useCases.deletePromotion.execute(promotionId, resource -> actionState.setValue(resource));
    }

    public void clearActionState() { actionState.setValue(null); }
}