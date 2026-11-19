package com.example.commerce;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // 카테고리별 상품 목록 데이터 생성
        Category electronics = new Category("전자제품", List.of(
            new Product("Galaxy S25",1200000,"최신 안드로이드 스마트폰",50),
            new Product("iPhone 16",1350000,"Apple 최신 스마트폰",50),
            new Product("MacBook Pro",2400000,"M3 칩셋 탑재 노트북",50),
            new Product("AirPods Pro",350000,"노이즈 캔슬링 무선 이어폰",50)
        ));

        Category clothes = new Category("의류", List.of(
            new Product("와이드 데님 팬츠",59000,"편안한 착용감의 청바지",50),
            new Product("오버핏 후드 집업",39000,"가볍게 입기 좋은 아우터",50),
            new Product("기모 맨투맨 ",34000,"포근한 소재로 되어있는 맨투맨",50),
            new Product("경량 구스다운 패딩",149000,"보온성이 뛰어난 겨울용 패딩",50)
        ));

        Category foods = new Category("식품", List.of(
            new Product("오뚜기 콤비네이션 피자",5000,"간편한 냉동 피자",50),
            new Product("CJ 스팸김치 볶음밥",6000,"4인분 개별 포장이 된 스팸김치 볶음밥",50),
            new Product("갑오징어 볶음",15000,"매콤한 양념이 된 오징어 볶음",50),
            new Product("닭다리살 정육",7000,"신선한 닭다리살 정육",50)
        ));

        List<Category> categories = new ArrayList<>(List.of(electronics, clothes, foods));

        // 카테고리 목록을 CommerceSystem 에 전달하며 객체 생성
        CommerceSystem commerceSystem = new CommerceSystem(categories);
        commerceSystem.start();
    }
}