package com.example.commerce;

import java.util.List;
import java.util.Scanner;

public class AdminSystem {
    private static final String ADMIN_PWD = "admin1234";
    private static final int MAX_ATTEMPTS = 3;
    private List<Category> categories;
    private Cart cart;
    private Scanner scanner;

    public AdminSystem(List<Category> categories, Cart cart, Scanner scanner) {
        this.categories = categories;
        this.cart = cart;
        this.scanner = scanner;
    }

    public void start() {
        if (!authenticate()) {
            return;
        }

        while (true) {
            System.out.println("[ 관리자 모드 ]");
            System.out.println("1. 상품 추가");
            System.out.println("2. 상품 수정");
            System.out.println("3. 상품 삭제");
            System.out.println("4. 전체 상품 현황");
            System.out.println("0. 메인으로 돌아가기");

            int input;

            while(true) {
                input = readIntInput();
                if (input >= 0 && input <= 4) {
                    break;
                } 
                System.out.println("잘못된 선택입니다. 다시 시도하세요.");
            }

            switch (input) {
                case 1 -> {
                    System.out.println("\n어느 카테고리에 상품을 추가하시겠습니까?");
                    for (int i=0; i<categories.size(); i++) {
                        System.out.printf("%d. %s\n", i+1, categories.get(i).getName());
                    }

                    while(true) {
                        input = readIntInput();
                        if (input > 0 && input <= categories.size()) {
                            break;
                        } 
                        System.out.println("잘못된 선택입니다. 다시 시도하세요.");
                    }
                    scanner.nextLine();
                    
                    System.out.printf("[ %s 카테고리에 상품 추가 ]\n", categories.get(input-1).getName());
                    System.out.print("상품명을 입력해주세요: ");
                    String inputName = scanner.nextLine();
                    System.out.print("가격을 입력해주세요: ");
                    int inputPrice = readIntInput();
                    scanner.nextLine();
                    System.out.print("상품 설명을 입력해주세요: ");
                    String inputDescription = scanner.nextLine();
                    System.out.print("재고수량을 입력해주세요: ");                    
                    int inputQuantity = readIntInput();
                    try {
                        categories.get(input-1).addProduct(new Product(inputName, inputPrice, inputDescription, inputQuantity));
                        System.out.println("\n상품이 성공적으로 추가되었습니다!");
                    } catch (IllegalArgumentException e) {
                        System.out.println("상품 추가 실패: " + e.getMessage() + "\n");
                    }
                }
                case 2 -> {
                    
                }
                case 3 -> {
                    
                }
                case 4 -> {
                    
                }
                case 0 -> {
                    return;
                }
            }
        }
    }

    public boolean authenticate() {
        for(int i=0; i<MAX_ATTEMPTS;i++) {
            System.out.print("\n관리자 비밀번호를 입력해주세요: ");
            String inputPwd = scanner.next();

            if (inputPwd.equals(ADMIN_PWD)) {
                return true;
            }
            System.out.printf("비밀번호가 일치하지 않습니다. (%d/%d)\n", i+1, MAX_ATTEMPTS );
        }
        System.out.println("시도 횟수를 초과하여 메인 메뉴로 돌아갑니다.\n");
        return false;
    }

    private int readIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.println("잘못된 입력입니다. 숫자를 입력하세요.");
            scanner.next();
        }
        return scanner.nextInt();
    }
}
