package com.example.dienmayxanh.features.categories.domain;

import com.example.dienmayxanh.features.categories.data.Category;
import com.example.dienmayxanh.features.categories.data.Category_Repository;

import java.util.List;

public class Get_Categories_UseCase {
    private Category_Repository repository;

    public Get_Categories_UseCase() {
        this.repository = new Category_Repository();
    }

    public void execute(Category_Repository.CategoryCallback<List<Category>> callback) {
        repository.getCategories(callback);
    }
}