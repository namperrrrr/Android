package com.example.dienmayxanh.features.categories.domain;

import com.example.dienmayxanh.features.categories.data.Category_Repository;

public class Delete_Category_UseCase {
    private Category_Repository repository = new Category_Repository();

    public void execute(String categoryId, Category_Repository.CategoryCallback<Void> callback) {
        repository.deleteCategory(categoryId, callback);
    }
}