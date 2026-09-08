package com.example.dienmayxanh.features.products.domain;

public class Product_UseCases {
    public Get_Products_UseCase getProducts = new Get_Products_UseCase();
    public Add_Product_UseCase addProduct = new Add_Product_UseCase();
    public Update_Product_UseCase updateProduct = new Update_Product_UseCase();
    public Delete_Product_UseCase deleteProduct = new Delete_Product_UseCase();
}