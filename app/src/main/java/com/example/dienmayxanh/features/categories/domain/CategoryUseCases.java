package com.example.dienmayxanh.features.categories.domain;

public class CategoryUseCases {
    public GetCategoriesUseCase getCategories = new GetCategoriesUseCase();
    public AddCategoryUseCase addCategory = new AddCategoryUseCase();
    public UpdateCategoryUseCase updateCategory = new UpdateCategoryUseCase();
    public DeleteCategoryUseCase deleteCategory = new DeleteCategoryUseCase();
}