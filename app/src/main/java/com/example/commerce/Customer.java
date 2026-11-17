package com.example.commerce;

public class Customer {

    private String name;
    private String email;
    private Grade grade;

    // 생성자
    public Customer(String name, String email, Grade grade) {
        this.name = name;
        this.email = email;
        this.grade = grade;
    }

    // Getter
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Grade getGrade() {
        return grade;
    }
}
