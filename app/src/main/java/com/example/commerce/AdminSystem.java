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

                    Product newProduct = new Product(inputName, inputPrice, inputDescription, inputQuantity);
                    System.out.printf("\n%s | %,d원 | %s | 재고: %d개\n위 정보로 상품을 추가하시겠습니까?\n1. 확인     2. 취소\n", newProduct.getName(), newProduct.getPrice(), newProduct.getDescription(), newProduct.getQuantity());

                    int confirmInput = 0;
                    while((confirmInput = readIntInput()) < 1 || confirmInput > 2) {
                        System.out.println("잘못된 선택입니다. 다시 시도하세요.");
                    }

                    if(confirmInput == 2){
                        System.out.println("\n상품 추가가 취소되었습니다.\n");
                        continue;
                    }

                    try {
                        categories.get(input-1).addProduct(newProduct);
                        System.out.println("\n상품이 성공적으로 추가되었습니다!");
                    } catch (IllegalArgumentException e) {
                        System.out.println("상품 추가 실패: " + e.getMessage() + "\n");
                    }
                }
                case 2 -> {
                    scanner.nextLine();
                    System.out.print("수정할 상품명을 입력해주세요: ");
                    String inputName = scanner.nextLine();

                    try {
                        Product editProduct = findProduct(inputName);
                        System.out.printf("현재 상품 정보: %s | %,d원 | %s | 재고: %d개\n\n", editProduct.getName(), editProduct.getPrice(), editProduct.getDescription(), editProduct.getQuantity());
                        System.out.println("수정할 항목을 선택해주세요:");
                        System.out.println("1. 가격");
                        System.out.println("2. 설명");
                        System.out.println("3. 재고수량");

                        while((input = readIntInput()) < 1 || input > 3) {
                            System.out.println("잘못된 선택입니다. 다시 시도하세요.");
                        }

                        try {
                            switch(input) {
                                case 1 -> {
                                    int oldPrice = editProduct.getPrice();
                                    System.out.printf("현재 가격: %,d\n", oldPrice);
                                    System.out.print("새로운 가격을 입력해주세요: ");
                                    int newPrice = readIntInput();
                                    editProduct.setPrice(newPrice);
                                    System.out.printf("%s의 가격이 %,d원 -> %,d원으로 수정되었습니다.\n\n", editProduct.getName(), oldPrice,editProduct.getPrice());
                                }
                                case 2 -> {
                                    String oldDescription = editProduct.getDescription();
                                    System.out.printf("현재 설명: %s\n", oldDescription);
                                    System.out.print("새로운 설명을 입력해주세요: ");
                                    scanner.nextLine();
                                    String newDescription = scanner.nextLine();
                                    editProduct.setDescription(newDescription);
                                    System.out.printf("%s의 설명이 %s 으로 수정되었습니다.\n\n", editProduct.getName(), editProduct.getDescription());
                                }
                                case 3 -> {
                                    int oldQuantity = editProduct.getQuantity();
                                    System.out.printf("현재 재고수량: %,d\n", oldQuantity);
                                    System.out.print("새로운 재고수량을 입력해주세요: ");
                                    int newQuantity = readIntInput();
                                    editProduct.setQuantity(newQuantity);
                                    System.out.printf("%s의 재고수량이 %,d -> %,d 으로 수정되었습니다.\n\n", editProduct.getName(), oldQuantity, editProduct.getQuantity());
                                }
                            }
                        } catch (IllegalArgumentException e) {
                            System.out.println("수정 실패: " + e.getMessage() + "\n");
                        }

                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                }
                case 3 -> {
                    scanner.nextLine();
                    System.out.print("삭제할 상품명을 입력해주세요: ");
                    String inputName = scanner.nextLine();

                    try {
                        Product removeProduct = findProduct(inputName);
                        System.out.printf("현재 상품 정보: %s | %,d원 | %s | 재고: %d개\n\n", removeProduct.getName(), removeProduct.getPrice(), removeProduct.getDescription(), removeProduct.getQuantity());

                        System.out.printf("%s 상품을 정말로 삭제하시겠습니까?\n1. 확인     2. 취소\n", removeProduct.getName());

                        while((input = readIntInput()) < 1 || input > 2) {
                            System.out.println("잘못된 선택입니다. 다시 시도하세요.");
                        }

                        if(input == 2){
                            System.out.println("\n상품 삭제가 취소되었습니다.\n");
                            continue;
                        }

                        for (Category category : categories) {
                            if (category.getProducts().contains(removeProduct)) {
                                category.removeProduct(removeProduct);
                                break;
                            }
                        }
                        cart.removeProduct(removeProduct);
                        System.out.println("\n상품이 성공적으로 삭제되었습니다!\n");
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }                    
                }
                case 4 -> {
                    System.out.println("[ 전체 상품 현황 ]");
                    categories.stream()
                        .forEach(category -> {
                            System.out.printf("%s\n", category.getName());
                            List<Product> products = category.getProducts();
                            if(products.isEmpty()) {
                                System.out.println("등록된 상품이 없습니다.");
                                return;
                            }
                            products.stream()
                                .forEach(product -> 
                                    System.out.printf("- %-20s | %,10d원 | 재고: %d개 | %s\n", product.getName(), product.getPrice(), product.getQuantity(), product.getDescription())
                                );
                        });
                    System.out.println();
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

    private Product findProduct(String name) {
        for (Category category : categories) {
            for (Product product : category.getProducts()) {
                if (name.trim().equals(product.getName().trim())) {
                    return product;
                }
            }
        }
        throw new IllegalArgumentException("해당 상품을 찾을 수 없습니다.");
    }
}
