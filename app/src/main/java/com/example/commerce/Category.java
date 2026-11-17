package com.example.commerce;

import java.util.List;
import java.util.ArrayList;

public class Category {
    private String name;
    private List<Product> products;

    // 생성자
    public Category(String name, List<Product> products) {
        this.name = name;
        this.products = new ArrayList<>(products);
    }

    // Getter
    // 카테고리 이름 반환
    public String getName() {
        return name;
    }

    public List<Product> getProducts() {
        return List.copyOf(products);
    }    

    public void addProduct(Product newProduct) {
        if (newProduct == null) {
            throw new IllegalArgumentException("상품 정보가 없습니다");
        }
        
        for (Product product : products) {
            if (product.getName().equals(newProduct.getName())) {
                throw new IllegalArgumentException("이미 동일한 이름의 상품이 존재합니다.");
            }
        }
        products.add(newProduct);
    }
}