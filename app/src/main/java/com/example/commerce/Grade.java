package com.example.commerce;

public enum Grade {
    BRONZE(0),
    BASIC(5),
    PRO(10),
    VIP(15);

    private final int discountRate;

    Grade(int discountRate) {
        this.discountRate = discountRate;
    }

    public int getDiscountRate() {
        return discountRate;
    }

    @Override
    public String toString() {
        return String.format("%-10s : %5d%% 할인", this.name(), this.discountRate);
    }
}
