package com.example.commerce;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommerceSystem {
    private List<Category> categories;
    private Cart cart = new Cart();
    private Scanner scanner = new Scanner(System.in);

    // 생성자
    public CommerceSystem(List<Category> categories) {
        this.categories = new ArrayList<>(categories);
    }

    private int readIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.println("잘못된 입력입니다. 숫자를 입력하세요.");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private void addToCart(Product product) {        
        try {
            cart.addItem(product);
            System.out.printf("\n%s 상품이 장바구니에 추가되었습니다.\n\n", product.getName());
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }

    private void categoryMenu(Category category) {
        System.out.printf("\n[ %s 카테고리 ]\n", category.getName());                

        List<Product> products = category.getProducts();
        // 선택한 카테고리의 상품 목록 출력
        for (int i=0; i<products.size(); i++) {
            Product product = products.get(i);
            System.out.printf("%d. %-20s | %,10d원 | %s\n", i+1, product.getName(), product.getPrice(), product.getDescription());
        }
        System.out.println("0. 뒤로가기");

        // 상품 선택 후 상세 정보 출력
        while (true) {
            int productInput = readIntInput();

            if (productInput == 0) {
                System.out.println("");
                return;
            } else if (productInput < 1 || productInput > products.size()) {
                System.out.println("잘못된 선택입니다. 다시 시도하세요.");
                continue;
            } else {
                Product selectedProduct = products.get(productInput-1);
                System.out.printf("선택한 상품: %s | 가격: %,d원 | 설명: %s | 재고: %d\n", selectedProduct.getName(), selectedProduct.getPrice(), selectedProduct.getDescription(), selectedProduct.getQuantity());
                System.out.println("위 상품을 장바구니에 추가하시겠습니까?");
                System.out.println("1. 확인        2. 취소");
                
                while (true) {
                    int cartInput = readIntInput();
            
                    switch (cartInput) {
                        case 1 -> {
                            addToCart(selectedProduct);
                            return;
                        }
                        case 2 -> {
                            System.out.println("상품 추가가 취소되었습니다.");
                            return;
                        }
                        default -> {
                            System.out.println("잘못된 선택입니다. 다시 시도하세요.");
                        }
                    }
                }
            }
        }
    }

    private void confirmOrder() { 
        System.out.printf("\n주문이 완료되었습니다! 총 금액: %,d원\n", cart.getTotalPrice());
        for(CartItem item : cart.getItems()) {
            item.getProduct().decreaseQuantity(item.getQuantity());
            System.out.printf("%s 재고가 %d개 -> %d개로 업데이트되었습니다.\n",item.getProduct().getName(), item.getProduct().getQuantity() + item.getQuantity(), item.getProduct().getQuantity());
        }
        cart.clear();
        System.out.println();
    }

    private void checkCart() {
        System.out.println("\n아래와 같이 주문하시겠습니까?\n");
        System.out.println("[ 장바구니 내역 ]");

        List<CartItem> items = cart.getItems();
        for (CartItem item : items) {
            Product product = item.getProduct();
             System.out.printf("%-20s | %,10d원 | %s | 수량: %d개\n", product.getName(), product.getPrice(), product.getDescription(), item.getQuantity());
        }
        
        System.out.println("\n[ 총 주문 금액 ]");
        System.out.printf("%,d원\n", cart.getTotalPrice());

        System.out.println("\n1. 주문 확정      2. 메인으로 돌아가기");
        while (true) {
            int input = readIntInput();
            switch (input) {
                case 1 -> {
                    confirmOrder(); // 재고 차감, 장바구니 비우기 처리
                    return;
                }
                case 2 -> {
                    System.out.println();
                    return; // 메인 메뉴로 복귀
                }
                default -> System.out.println("잘못된 선택입니다. 다시 시도하세요.");
            }
        }
    }

    public void start() {
        while (true) {
            System.out.println("[실시간 커머스 플랫폼 메인]");
            for (int i=0; i<categories.size(); i++) {
                System.out.printf("%d. %s\n", i+1, categories.get(i).getName());
            }

            if(!cart.isEmpty()) {
                System.out.println("\n[ 주문 관리 ]");
                System.out.printf("%d. 장바구니 확인   | 장바구니 확인 후 주문합니다.\n",categories.size()+1);
                System.out.printf("%d. 주문 취소      | 진행중인 주문을 취소합니다.\n",categories.size()+2);
            }
            System.out.printf("0. %-10s | 프로그램 종료\n", "종료");

            int input = readIntInput();
            
            if (input == 0) {                
                System.out.println("\n커머스 플랫폼을 종료합니다.");
                return;
            } else if (input >= 1 && input <= categories.size()) {
                // 카테고리 선택
                Category selectedCategory = categories.get(input-1);
                categoryMenu(selectedCategory);
            } else if (input == categories.size() + 1) {
                // 장바구니 확인
                checkCart();
            } else if (input == categories.size() + 2) {
                // 주문 취소
                cart.clear();
                System.out.println("장바구니를 초기화합니다.\n");
                continue;
            } else {
                System.out.println("잘못된 선택입니다. 다시 시도하세요.");
                continue;
            }
        }
    }
}