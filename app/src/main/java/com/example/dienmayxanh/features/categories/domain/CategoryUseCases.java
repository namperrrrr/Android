package com.example.dienmayxanh.features.categories.domain;

import com.example.dienmayxanh.features.categories.data.Category;
import com.example.dienmayxanh.features.categories.data.CategoryRepository;

import java.util.List;

public class CategoryUseCases {

    private final CategoryRepository repository;

    public CategoryUseCases() {
        this.repository = new CategoryRepository();
    }

    public void getCategories(CategoryRepository.CategoryCallback<List<Category>> callback) {
        repository.getCategories(callback);
    }

    public void addCategory(Category category, CategoryRepository.CategoryCallback<String> callback) {
        repository.addCategory(category, callback);
    }

    public void updateCategory(Category category, CategoryRepository.CategoryCallback<String> callback) {
        repository.updateCategory(category, callback);
    }

    public void deleteCategory(String categoryId, CategoryRepository.CategoryCallback<Void> callback) {
        repository.deleteCategory(categoryId, callback);
    }
}