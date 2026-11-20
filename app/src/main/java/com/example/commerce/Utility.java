package com.example.commerce;

import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
import java.util.Comparator;

public class Utility {
    // 사용자 입력을 여러 클래스에서 공통적으로 관리하기 위한 공유 Scanner
    private static final Scanner scanner = new Scanner(System.in);

    // 인스턴스화 금지
    private Utility() {}

    // 정수 입력을 처리하고 잘못된 입력 시 다시 입력받음
    public static int readIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.println("잘못된 입력입니다. 숫자를 입력하세요.");
            scanner.next();
        }
        int input = scanner.nextInt();
        scanner.nextLine();
        return input;
        
    }

    // 지정된 범위 또는 허용된 값에 해당하는 정수만 입력받음
    public static int readIntRange(int min, int max, int... extra) {
        while (true) {
            int input = readIntInput();
            if (input >= min && input <= max) return input;

            for (int ex : extra) {
                if (input == ex) return input;
            }
            System.out.println("잘못된 선택입니다. 다시 시도하세요.");
        }
    }

    // 문자열 입력
    public static String readLine() {
        return scanner.nextLine();
    }

    // 다양한 타입의 리스트를 전달받아 앞에 번호를 붙여 출력
    public static <T> void printNumberedList (List<T> list) {
        for (int i =0; i < list.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, list.get(i));
        }
    }

    // Function을 전달받아 리스트 요소를 원하는 문자열 형태로 변환하여 출력
    public static <T> void printNumberedList (List<T> list, Function<T, String> function) {
        for (int i =0; i < list.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, function.apply(list.get(i)));
        }
    }

    public static <T extends Printable> void printPrintableList(List<T> list) {
        printNumberedList(list, item -> item.toFormattedString());
    }

    // 정렬 옵션: 1 = 상품명 가나다 순, 2 = 가격 낮은 순, 3 = 가격 높은 순, 4 = 재고 적은 순, 5 = 재고 많은 순
    public static List<Product> sortProducts(List<Product> products, int sortOption) {
        Comparator<Product> priceComparator = Comparator.comparingInt(product -> product.getPrice());
        Comparator<Product> quantityComparator = Comparator.comparingInt(product -> product.getQuantity());

        return switch (sortOption) {
            case 1 ->
                products.stream()
                    .sorted(Comparator.comparing(product -> product.getName()))
                    .toList();
            case 2 ->
                products.stream()
                    .sorted(priceComparator)
                    .toList();
            case 3 ->
                products.stream()
                    .sorted(priceComparator.reversed())
                    .toList();
            case 4 ->
                products.stream()
                    .sorted(quantityComparator)
                    .toList();
            case 5 ->
                products.stream()
                    .sorted(quantityComparator.reversed())
                    .toList();
            default -> throw new IllegalArgumentException("잘못된 정렬 옵션입니다.");
        };
    }

    public static List<Product> searchProducts(List<Product> products, String keyword) {
        String lowerKeyword = keyword.toLowerCase().trim();
        return products.stream()
            .filter(product -> product.getName().contains(lowerKeyword) || product.getDescription().contains(lowerKeyword))
            .toList();
    }
}