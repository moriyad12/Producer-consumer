package com.example.demo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CareTaker {
    private List<Product> Products = new ArrayList<>();

    public CareTaker(List<Product> products) {
        Products = products;
    }

    public List<Product> getProducts() {
        return Products;
    }

    public void setProducts(List<Product> products) {
        Products = products;
    }

    public void AddProduct(Product p) {
        this.Products.add(p);
    }

    public void RemoveProduct(Product p) {
        this.Products.remove(p);
    }
}
