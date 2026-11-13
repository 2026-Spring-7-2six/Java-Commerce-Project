package com.example.commerce;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<Product> products = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        products.add(new Product("Galaxy S25",1200000,"최신 안드로이드 스마트폰",50));
        products.add(new Product("iPhone 16",1350000,"Apple 최신 스마트폰",50));
        products.add(new Product("MacBook Pro",2400000,"M3 칩셋 탑재 노트북",50));
        products.add(new Product("AirPods Pro",350000,"노이즈 캔슬링 무선 이어폰",50));

        for (int i=0; i<products.size(); i++) {
            System.out.printf("%d. %-20s | %,10d원 | %s\n", i+1, products.get(i).getName(), products.get(i).getPrice(), products.get(i).getDescription());
        }
        System.out.printf("0. %-20s | 프로그램 종료\n", "종료");

        int input;

        while (true) {
            if(!scanner.hasNextInt()) {
                System.out.println("잘못된 입력입니다. 숫자를 입력하세요.");
                scanner.next();
                continue;
            }
            input = scanner.nextInt();                

            switch (input) {
                case 0 -> {
                    System.out.println("\n커머스 플랫폼을 종료합니다.");
                    return;
                }
                default -> {
                    System.out.println("잘못된 선택입니다. 다시 시도하세요.");
                }
            }
        }
    }
}