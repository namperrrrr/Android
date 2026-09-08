package com.example.dienmayxanh.features.categories.domain;

import com.example.dienmayxanh.features.categories.data.Category;
import com.example.dienmayxanh.features.categories.data.Category_Repository;

public class Update_Category_UseCase {
    private Category_Repository repository = new Category_Repository();

    public void execute(Category category, Category_Repository.CategoryCallback<String> callback) {
        repository.updateCategory(category, callback);
    }
}