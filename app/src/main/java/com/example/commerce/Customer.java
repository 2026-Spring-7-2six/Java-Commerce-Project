package com.example.commerce;

public class Customer {

    public enum Grade {
        GUEST, BASIC, PRO, VIP
    }

    private String name;
    private String email;
    private Grade grade;
}
