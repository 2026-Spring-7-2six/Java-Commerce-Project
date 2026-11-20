package com.example.commerce;

import java.util.List;

public class AdminSystem {
    private static final String ADMIN_PWD = "admin1234";
    private static final int MAX_ATTEMPTS = 3;
    private List<Category> categories;
    private Cart cart;

    public AdminSystem(List<Category> categories, Cart cart) {
        this.categories = categories;
        this.cart = cart;
    }

    public void start() {
        // 관리자 인증
        if (!authenticate()) {
            return;
        }

        while (true) {
            System.out.println("[ 관리자 모드 ]");
            Utility.printNumberedList(List.of("상품 추가", "상품 수정", "상품 삭제", "전체 상품 현황"));
            System.out.println("0. 메인으로 돌아가기");

            int input = Utility.readIntRange(0,4);

            switch (input) {
                case 1 -> addProduct();
                case 2 -> editProduct();
                case 3 -> removeProduct();
                case 4 -> printAllProducts();
                case 0 -> {
                    System.out.println();
                    return;
                }
            }
        }
    }

    // 선택한 카테고리에 새로운 상품 추가
    private void addProduct() {
        System.out.println("\n어느 카테고리에 상품을 추가하시겠습니까?");
        Utility.printPrintableList(categories);

        int input = Utility.readIntRange(1, categories.size());
        
        // 상품 정보 입력
        System.out.printf("[ %s 카테고리에 상품 추가 ]\n", categories.get(input-1).getName());
        System.out.print("상품명을 입력해주세요: ");
        String inputName = Utility.readLine();
        System.out.print("가격을 입력해주세요: ");
        int inputPrice = Utility.readIntInput();
        System.out.print("상품 설명을 입력해주세요: ");
        String inputDescription = Utility.readLine();
        System.out.print("재고수량을 입력해주세요: ");                    
        int inputQuantity = Utility.readIntInput();

        // 입력한 정보로 객체 생성 후 추가 여부 확인
        Product newProduct = new Product(inputName, inputPrice, inputDescription, inputQuantity);
        System.out.printf("\n%s | %,d원 | %s | 재고: %d개\n위 정보로 상품을 추가하시겠습니까?\n1. 확인     2. 취소\n", newProduct.getName(), newProduct.getPrice(), newProduct.getDescription(), newProduct.getQuantity());

        int confirmInput = Utility.readIntRange(1,2);

        if(confirmInput == 2){
            System.out.println("\n상품 추가가 취소되었습니다.\n");
            return;
        }

        // 카테고리에 상품 추가 및 중복 등 예외 처리
        try {
            categories.get(input-1).addProduct(newProduct);
            System.out.println("\n상품이 성공적으로 추가되었습니다!");
        } catch (IllegalArgumentException e) {
            System.out.println("상품 추가 실패: " + e.getMessage() + "\n");
        }
    }

    // 상품명으로 찾아서 해당 상품 정보 수정
    private void editProduct() {
        System.out.print("수정할 상품명을 입력해주세요: ");
        String inputName = Utility.readLine();

        Product editProduct;
        try {
            editProduct = findProduct(inputName);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return; // 에러 발생 시 바로 종료하여 깊이를 줄임
        }

        // 상품 정보를 출력하고 수정할 항목 선택
        System.out.printf("현재 상품 정보: %s | %,d원 | %s | 재고: %d개\n\n", editProduct.getName(), editProduct.getPrice(), editProduct.getDescription(), editProduct.getQuantity());
        System.out.println("수정할 항목을 선택해주세요:");
        Utility.printNumberedList(List.of("가격", "설명", "재고수량"));

        int input = Utility.readIntRange(1, 3);

        // 선택한 항목을 새로운 값으로 수정
        try {
            switch(input) {
                case 1 -> {
                    int oldPrice = editProduct.getPrice();
                    System.out.printf("현재 가격: %,d\n", oldPrice);
                    System.out.print("새로운 가격을 입력해주세요: ");
                    int newPrice = Utility.readIntInput();
                    editProduct.setPrice(newPrice);
                    System.out.printf("%s의 가격이 %,d원 -> %,d원으로 수정되었습니다.\n\n", editProduct.getName(), oldPrice,editProduct.getPrice());
                }
                case 2 -> {
                    String oldDescription = editProduct.getDescription();
                    System.out.printf("현재 설명: %s\n", oldDescription);
                    System.out.print("새로운 설명을 입력해주세요: ");
                    String newDescription = Utility.readLine();
                    editProduct.setDescription(newDescription);
                    System.out.printf("%s의 설명이 %s 으로 수정되었습니다.\n\n", editProduct.getName(), editProduct.getDescription());
                }
                case 3 -> {
                    int oldQuantity = editProduct.getQuantity();
                    System.out.printf("현재 재고수량: %,d\n", oldQuantity);
                    System.out.print("새로운 재고수량을 입력해주세요: ");
                    int newQuantity = Utility.readIntInput();
                    editProduct.setQuantity(newQuantity);
                    System.out.printf("%s의 재고수량이 %,d -> %,d 으로 수정되었습니다.\n\n", editProduct.getName(), oldQuantity, editProduct.getQuantity());
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("수정 실패: " + e.getMessage() + "\n");
        }
    }

    // 상품명으로 찾아서 해당 상품 삭제
    private void removeProduct() {
        System.out.print("삭제할 상품명을 입력해주세요: ");
        String inputName = Utility.readLine();

        Product removeProduct;
        try {
            removeProduct = findProduct(inputName);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return; // 에러 발생 시 바로 종료하여 깊이를 줄임
        }
        
        // 삭제할 상품 정보 출력 및 삭제 여부 확인
        System.out.printf("현재 상품 정보: %s | %,d원 | %s | 재고: %d개\n\n", removeProduct.getName(), removeProduct.getPrice(), removeProduct.getDescription(), removeProduct.getQuantity());
        System.out.printf("%s 상품을 정말로 삭제하시겠습니까?\n1. 확인     2. 취소\n", removeProduct.getName());

        int input = Utility.readIntRange(1,2);

        if(input == 2){
            System.out.println("\n상품 삭제가 취소되었습니다.\n");
            return;
        }

        for (Category category : categories) {
            if (category.getProducts().contains(removeProduct)) {
                category.removeProduct(removeProduct);
                break;
            }
        }
        cart.removeProduct(removeProduct);
        System.out.println("\n상품이 성공적으로 삭제되었습니다!\n");                   
    }

    // 모든 상품 정보 출력
    private void printAllProducts() {
        System.out.println("\n[ 전체 상품 현황 ]");
        categories.stream()
            .forEach(category -> {
                System.out.printf("--- %s ---\n", category.getName());
                List<Product> products = category.getProducts();
                if(products.isEmpty()) {
                    System.out.println("등록된 상품이 없습니다.");
                    return;
                }
                products.stream()
                    .forEach(product -> 
                        System.out.printf("- %-20s | %,10d원 | 재고: %d개 | %s\n", product.getName(), product.getPrice(), product.getQuantity(), product.getDescription())
                    );                
                System.out.println();
            });
    }

    // 관리자 비밀번호로 인증, 최대 시도 횟수 초과 시 인증 실패
    public boolean authenticate() {
        for(int i=0; i<MAX_ATTEMPTS;i++) {
            System.out.print("\n관리자 비밀번호를 입력해주세요: ");
            String inputPwd = Utility.readLine();

            if (inputPwd.equals(ADMIN_PWD)) {
                return true;
            }
            System.out.printf("비밀번호가 일치하지 않습니다. (%d/%d)\n", i+1, MAX_ATTEMPTS );
        }
        System.out.println("시도 횟수를 초과하여 메인 메뉴로 돌아갑니다.\n");
        return false;
    }    

    // 모든 카테고리의 상품을 검색하여 상품명과 일치하는 상품 반환
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
