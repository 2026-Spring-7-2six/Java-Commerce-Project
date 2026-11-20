package com.example.commerce;

import java.util.ArrayList;
import java.util.List;

public class CommerceSystem {
    private List<Category> categories;
    private Cart cart = new Cart();
    private AdminSystem adminSystem;
    
    // 생성자
    public CommerceSystem(List<Category> categories) {
        this.categories = new ArrayList<>(categories);
        this.adminSystem = new AdminSystem(categories, cart);
    }

    public void start() {
        while (true) {
            System.out.println("[실시간 커머스 플랫폼 메인]");
            Utility.printPrintableList(categories);

            // 장바구니에 상품이 있으면 주문 관리 메뉴 출력
            if(!cart.isEmpty()) {
                System.out.println("\n[ 주문 관리 ]");
                System.out.printf("%d. 장바구니 확인   | 장바구니 확인 후 주문합니다.\n",categories.size()+1);
                System.out.printf("%d. 주문 취소      | 진행중인 주문을 취소합니다.\n",categories.size()+2);
            }
            System.out.printf("0. %-10s | 프로그램 종료\n", "종료");
            System.out.printf("%d. 관리자 모드\n",categories.size()+3);

            // 장바구니에 상품이 있으면 선택 가능한 메뉴 범위를 확장
            int maxNum = cart.isEmpty() ? categories.size() : categories.size() + 3;
            int input = Utility.readIntRange(0, maxNum, categories.size() + 3);
            
            if (input == 0) {                
                System.out.println("\n커머스 플랫폼을 종료합니다.");
                return;
            } else if (input <= categories.size()) { // 선택한 카테고리 상품 메뉴
                categoryMenu(categories.get(input-1));
            } else if (input == categories.size() + 1) { // 장바구니 확인 및 주문 진행
                checkCart();
            } else if (input == categories.size() + 2) { // 주문 취소
                cancelOrder();
                continue;
            } else if (input == categories.size() + 3) { // 관리자 모드 진입
                adminSystem.start();
            }
        }
    }

    // 상품을 장바구니에 추가
    private void addToCart(Product product) {        
        try {
            cart.addItem(product);
            System.out.printf("\n%s 상품이 장바구니에 추가되었습니다.\n\n", product.getName());
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage()+"\n");
        }
    }

    // 상품 목록을 출력하고 선택한 상품을 장바구니에 추가
    private void selectAndAddToCart(List<Product> products) {  
        if (products.isEmpty()) {
            System.out.println("조건에 맞는 상품이 없습니다.\n");
            return;
        }

        System.out.println();
        Utility.printNumberedList(products, p -> String.format("%-20s | %,10d원 | %s", p.getName(), p.getPrice(), p.getDescription()));
        System.out.println("0. 뒤로가기");

        int productInput = Utility.readIntRange(0, products.size());

        if (productInput == 0) {
            System.out.println();
            return;
        } 

        // 선택한 상품의 정보 출력
        Product selectedProduct = products.get(productInput-1);
        System.out.printf("선택한 상품: %s | 가격: %,d원 | 설명: %s | 재고: %d\n", selectedProduct.getName(), selectedProduct.getPrice(), selectedProduct.getDescription(), selectedProduct.getQuantity());
        System.out.println("위 상품을 장바구니에 추가하시겠습니까?");
        System.out.println("1. 확인        2. 취소");
        
        int cartInput = Utility.readIntRange(1, 2);
    
        switch (cartInput) {
            case 1 -> addToCart(selectedProduct);
            case 2 -> System.out.println("상품 추가가 취소되었습니다.\n");
        }        
        return;
    }

    // 카테고리 상품 목록 및 가격 필터링 메뉴 출력
    private void categoryMenu(Category category) {
        List<Product> products = category.getProducts();

        System.out.printf("\n[ %s 카테고리 ]\n", category.getName());
        Utility.printNumberedList(List.of("전체 상품 보기","가격대별 필터링 (100만원 이하)","가격대별 필터링 (100만원 초과)"));
        System.out.println("0. 뒤로가기");

        int input = Utility.readIntRange(0, 3);

        if (input == 0) {
            System.out.println();
            return;
        }

        // 선택에 따라 가격 필터링
        List<Product> targetProducts = switch (input) {
            case 2 -> products.stream()
                    .filter(product -> product.getPrice() <= 1000000)
                    .toList();
            case 3 -> products.stream()
                    .filter(product -> product.getPrice() > 1000000)
                    .toList();
            default -> products;
        };
        selectAndAddToCart(targetProducts);
    }

    // 주문 확정 및 금액 계산 후 재고 차감
    private void confirmOrder(Grade grade) { 
        try {
            System.out.println("\n주문이 완료되었습니다!");
            System.out.printf("할인 전 금액: %,d원\n", cart.getTotalPrice());

            // 고객 등급에 따른 할인 금액 계산
            int discountPrice = cart.getTotalPrice() * grade.getDiscountRate() / 100;
            System.out.printf("%s 등급 할인(%d%%): -%,d원\n", grade.name(), grade.getDiscountRate(), discountPrice);
            System.out.printf("최종 결제 금액: %,d원\n", cart.getTotalPrice() - discountPrice);
            cart.getItems().stream()
                .forEach(item -> {
                    Product product = item.getProduct();
                    product.decreaseQuantity(item.getQuantity());
                    System.out.printf("%s 재고가 %d개 -> %d개로 업데이트되었습니다.\n",product.getName(), product.getQuantity() + item.getQuantity(), product.getQuantity());
            });
            cart.clear();
        } catch (IllegalStateException e) {
            System.out.println("\n주문 실패: " + e.getMessage());
        }
        System.out.println();
    }

    // 장바구니 내역 및 주문 여부 결정
    private void checkCart() {
        System.out.println("\n아래와 같이 주문하시겠습니까?\n");
        System.out.println("[ 장바구니 내역 ]");

        printCart();
        
        System.out.println("\n[ 총 주문 금액 ]");
        System.out.printf("%,d원\n", cart.getTotalPrice());

        System.out.println("\n1. 주문 확정      2. 메인으로 돌아가기");

        int input = Utility.readIntRange(1,2);

        switch (input) {
            case 1 -> confirmOrder(choiceGrade()); // 재고 차감, 장바구니 비우기 처리                
            case 2 -> System.out.println();
        }
        return;
    }

    // 장바구니 상품 정보 출력
    private void printCart() {   
        cart.getItems().stream()
            .forEach(item -> {
                Product product = item.getProduct();
                System.out.printf("%-20s | %,10d원 | %s | 수량: %d개\n", product.getName(), product.getPrice(), product.getDescription(), item.getQuantity());
        });
    }

    // 고객 등급 선택
    private Grade choiceGrade() {
        System.out.println("\n고객 등급을 입력해주세요.");

        Utility.printPrintableList(List.of(Grade.values()));

        int input = Utility.readIntRange(1, Grade.values().length);
        return Grade.values()[input -1];
    }

    // 장바구니 전체 취소 또는 개별 상품 취소
    private void cancelOrder() {
        if (cart.isEmpty()) {
            System.out.println("장바구니가 비어있습니다.\n");
        }

        System.out.println("\n[ 주문 취소 ]");
        Utility.printNumberedList(List.of("전체 주문 취소", "개별 상품 제거"));
        System.out.println("0. 뒤로가기");

        int input = Utility.readIntRange(0, 2);

        switch (input) {
            case 1 -> { // 장바구니 전체 초기화
                cart.clear();
                System.out.println("\n장바구니를 초기화합니다.\n");
            }
            case 2 -> { // 선택한 상품만 장바구니에서 제거
                System.out.println();
                Utility.printPrintableList(cart.getItems());
                System.out.printf("제거할 상품을 선택하세요: ");

                int removeInput = Utility.readIntRange(1, cart.getItems().size());
                Product removeProduct = cart.getItems().get(removeInput - 1).getProduct();
                cart.removeProduct(removeProduct);
                System.out.printf("\n'%s' 상품이 장바구니에서 제거되었습니다.\n\n", removeProduct.getName());
            }
            case 0 -> System.out.println();
        }        
    }
}