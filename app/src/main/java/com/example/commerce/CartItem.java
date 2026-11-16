package com.example.commerce;

public class CartItem {
    private final Product product;
    private int quantity;

    // 생성자
    public CartItem(Product product) {
        this.product = product;
        this.quantity = 1;
    }

    // Getter
    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    // 수량 증가
    public void increaseQuantity(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("음수를 넣을 수 없습니다.");
        }
        quantity += amount;
    }
}