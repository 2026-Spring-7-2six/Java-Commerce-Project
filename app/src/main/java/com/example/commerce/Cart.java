package com.example.commerce;

import java.util.List;
import java.util.ArrayList;

public class Cart {
    private List<CartItem> items;
    
    // 생성자
    public Cart() {
        this.items = new ArrayList<>();
    }

    // 장바구니에 상품 추가
    public void addItem(Product product) {
        for (CartItem item : items) { // 장바구니에 동일한 상품이 있는지 확인
            if (item.getProduct().equals(product)) {
                if (item.getQuantity() >= product.getQuantity()) { // 장바구니 수량이 상품 재고 이상이면 추가 불가
                    throw new IllegalStateException("재고가 부족하여 장바구니에 더 이상 담을 수 없습니다.");
                }
                item.increaseQuantity(1);
                return;
            }
        }
        
        // 재고 부족 시 예외 처리
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

    // 장바구니 총 금액 계산
    public int getTotalPrice() {
        int total = 0;
        for (CartItem item : items) {
            total += item.getProduct().getPrice() * item.getQuantity();
        }
        return total;
    }

    // 특정 상품을 장바구니에서 제거
    public void removeProduct(Product targetProduct) {
        items.removeIf(item -> targetProduct.equals(item.getProduct()));
    }
}
