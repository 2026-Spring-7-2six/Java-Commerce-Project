package com.example.commerce;

import java.util.List;
import java.util.Scanner;

public class CommerceSystem {
    private List<Product> products;

    // 생성자
    public CommerceSystem(List<Product> products) {
        this.products = products;
    }

    public void start() {    
        Scanner scanner = new Scanner(System.in);

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
