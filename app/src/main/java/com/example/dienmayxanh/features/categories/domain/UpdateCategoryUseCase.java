package com.example.dienmayxanh.features.categories.domain;

import com.example.dienmayxanh.features.categories.data.Category;
import com.example.dienmayxanh.features.categories.data.CategoryRepository;

public class UpdateCategoryUseCase {
    private CategoryRepository repository = new CategoryRepository();

    public void execute(Category category, CategoryRepository.CategoryCallback<String> callback) {
        repository.updateCategory(category, callback);
    }
}