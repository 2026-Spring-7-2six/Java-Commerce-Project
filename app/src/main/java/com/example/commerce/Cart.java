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
                item.increaseQuantity();
                return;
            }
        }
        items.add(new CartItem(product));
    }

    public List<CartItem> getItems() {
        return List.copyOf(items);
    }

    public void clear() {
        items.clear();
    }

    public int getTotalPrice() {
        int total = 0;
        for (CartItem item : items) {
            total += item.getProduct().getPrice() * item.getQuantity();
        }
        return total;
    }
}
