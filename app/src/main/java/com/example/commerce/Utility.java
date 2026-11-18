package com.example.commerce;

import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public class Utility {
    private static final Scanner scanner = new Scanner(System.in);

    // 인스턴스화 금지
    private Utility() {}

    public static int readIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.println("잘못된 입력입니다. 숫자를 입력하세요.");
            scanner.next();
        }
        int input = scanner.nextInt();
        scanner.nextLine();
        return input;
        
    }

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

    public static String readLine() {
        return scanner.nextLine();
    }

    public static <T> void printNumberedList (List<T> list) {
        for (int i =0; i < list.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, list.get(i));
        }
    }

    public static <T> void printNumberedList (List<T> list, Function<T, String> function) {
        for (int i =0; i < list.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, function.apply(list.get(i)));
        }
    }
}
