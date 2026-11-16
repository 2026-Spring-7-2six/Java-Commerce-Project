package com.example.commerce;

public class Product {
    private String name;
    private int price;
    private String description;
    private int quantity;

    // 생성자
    public Product(String name, int price, String description, int quantity) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.quantity = quantity;
    }

    // Getter
    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void decreaseQuantity(int amount) {
        if (amount <= quantity) {
            quantity -= amount;
        } else {
            System.out.println("재고가 부족합니다.");
        }
    }
}
