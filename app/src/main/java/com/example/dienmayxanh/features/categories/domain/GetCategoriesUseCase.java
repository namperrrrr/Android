package com.example.dienmayxanh.features.categories.domain;

import com.example.dienmayxanh.features.categories.data.Category;
import com.example.dienmayxanh.features.categories.data.CategoryRepository;

import java.util.List;

public class GetCategoriesUseCase {
    private CategoryRepository repository;

    public GetCategoriesUseCase() {
        this.repository = new CategoryRepository();
    }

    public void execute(CategoryRepository.CategoryCallback<List<Category>> callback) {
        repository.getCategories(callback);
    }
}