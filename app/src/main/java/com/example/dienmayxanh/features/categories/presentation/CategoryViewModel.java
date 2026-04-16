package com.example.dienmayxanh.features.categories.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dienmayxanh.core.network.Resource;
import com.example.dienmayxanh.features.categories.data.Category;
import com.example.dienmayxanh.features.categories.domain.CategoryUseCases;

import java.util.List;

public class CategoryViewModel extends ViewModel {
    private CategoryUseCases useCases = new CategoryUseCases();

    private final MutableLiveData<Resource<Void>> deleteCategoryState = new MutableLiveData<>();
    private MutableLiveData<Resource<List<Category>>> categoryListState = new MutableLiveData<>();

    public LiveData<Resource<List<Category>>> getCategoryListState() { return categoryListState; }
    public LiveData<Resource<Void>> getDeleteCategoryState() { return deleteCategoryState; }

    private MutableLiveData<Resource<String>> actionState = new MutableLiveData<>();
    public LiveData<Resource<String>> getActionState() { return actionState; }

    public void fetchCategories() {
        categoryListState.setValue(Resource.loading());
        useCases.getCategories.execute(resource -> categoryListState.setValue(resource));
    }

    public void addCategory(Category category) {
        actionState.setValue(Resource.loading());
        useCases.addCategory.execute(category, resource -> actionState.setValue(resource));
    }

    public void updateCategory(Category category) {
        actionState.setValue(Resource.loading());
        useCases.updateCategory.execute(category, resource -> actionState.setValue(resource));
    }

    public void deleteCategory(String categoryId) {
        deleteCategoryState.setValue(Resource.loading());
        useCases.deleteCategory.execute(categoryId, resource -> deleteCategoryState.setValue(resource));
    }

    public void clearActionState() {
        actionState.setValue(null);
    }
}