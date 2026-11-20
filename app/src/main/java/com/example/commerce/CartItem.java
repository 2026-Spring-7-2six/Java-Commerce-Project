package com.example.commerce;

public class CartItem implements Printable {
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

    @Override     
    public String toFormattedString(){
        return String.format("%-20s | %,10d원 | %s | 수량: %d개",product.getName(), product.getPrice(), product.getDescription(), this.quantity);
    }
}