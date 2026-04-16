package com.example.dienmayxanh.features.categories.domain;

import com.example.dienmayxanh.features.categories.data.CategoryRepository;

public class DeleteCategoryUseCase {
    private CategoryRepository repository = new CategoryRepository();

    public void execute(String categoryId, CategoryRepository.CategoryCallback<Void> callback) {
        repository.deleteCategory(categoryId, callback);
    }
}