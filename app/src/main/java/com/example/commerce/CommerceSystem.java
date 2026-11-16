package com.example.commerce;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommerceSystem {
    private List<Category> categories;

    // 생성자
    public CommerceSystem(List<Category> categories) {
        this.categories = new ArrayList<>(categories);
    }

    public void start() {    
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("[실시간 커머스 플랫폼 메인]");
            for (int i=0; i<categories.size(); i++) {
                System.out.printf("%d. %s\n", i+1, categories.get(i).getName());
            }
            System.out.printf("0. %-10s | 프로그램 종료\n", "종료");

            int input;

            if(!scanner.hasNextInt()) {
                System.out.println("잘못된 입력입니다. 숫자를 입력하세요.");
                scanner.next();
                continue;
            }
            input = scanner.nextInt();   
            
            if (input == 0) {
                System.out.println("\n커머스 플랫폼을 종료합니다.");
                return;
            } else if (input < 1 || input > categories.size()) {
                System.out.println("잘못된 선택입니다. 다시 시도하세요.");
                continue;
            } else { // 카테고리 목록 출력 후 카테고리 선택
                Category selectedCategory = categories.get(input-1);
                System.out.printf("\n[ %s 카테고리 ]\n", selectedCategory.getName());                

                List<Product> products = selectedCategory.getProducts();
                // 선택한 카테고리의 상품 목록 출력
                for (int i=0; i<products.size(); i++) {
                    Product product = products.get(i);
                    System.out.printf("%d. %-20s | %,10d원 | %s\n", i+1, product.getName(), product.getPrice(), product.getDescription());
                }
                System.out.println("0. 뒤로가기");

                // 상품 선택 후 상세 정보 출력
                while (true) {
                    if(!scanner.hasNextInt()) {
                        System.out.println("잘못된 입력입니다. 숫자를 입력하세요.");
                        scanner.next();
                        continue;
                    }                
                    int productInput = scanner.nextInt();
                    
                    if (productInput == 0) {
                        System.out.println("");
                        break;
                    } else if (productInput < 1 || productInput > products.size()) {
                        System.out.println("잘못된 선택입니다. 다시 시도하세요.");
                        continue;
                    } else {
                        Product selectedProduct = products.get(productInput-1);
                        System.out.printf("선택한 상품: %s | 가격: %,d원 | 설명: %s | 재고: %d\n\n", selectedProduct.getName(), selectedProduct.getPrice(), selectedProduct.getDescription(), selectedProduct   .getQuantity());
                        break;
                    }
                }
            }
        }
    }
}