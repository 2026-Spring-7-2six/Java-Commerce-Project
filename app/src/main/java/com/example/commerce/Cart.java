package com.example.commerce;

import java.util.List;
import java.util.ArrayList;

public class Cart {
    private List<CartItem> items;
    
    // 생성자
    public Cart() {
        this.items = new ArrayList<>();
    }

    public void addItem(Product product) {
        for (CartItem item : items) {
            if (item.getProduct().equals(product)) {
                if (item.getQuantity() >= product.getQuantity()) {
                    throw new IllegalStateException("재고가 부족하여 장바구니에 더 이상 담을 수 없습니다.");
                }
                item.increaseQuantity(1);
                return;
            }
        }
        
        if (product.getQuantity() <= 0) {
            throw new IllegalStateException("재고가 부족하여 장바구니에 담을 수 없습니다.");
        }
        items.add(new CartItem(product));
    }

    public List<CartItem> getItems() {
        return List.copyOf(items);
    }

    public void clear() {
        items.clear();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public int getTotalPrice() {
        int total = 0;
        for (CartItem item : items) {
            total += item.getProduct().getPrice() * item.getQuantity();
        }
        return total;
    }
}
