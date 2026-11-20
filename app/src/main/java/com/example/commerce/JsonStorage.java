package com.example.commerce;

import com.google.gson.Gson; // JSON 변환
import com.google.gson.GsonBuilder; // JSON 출력 형식 설정
import com.google.gson.reflect.TypeToken; // 제네릭 타입 정보 보존

import java.lang.reflect.Type; // 타입 정보 표현
import java.nio.file.Files; // 파일 읽기 및 쓰기
import java.nio.file.Path; // 파일 경로 표현
import java.io.IOException; // 파일 입출력 예외
import java.util.List;

public class JsonStorage {
    private static final Path JSON_PATH = Path.of("data","products.json");
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public void save(List<Category> categories) {
        try {
            if (JSON_PATH.getParent() != null) {
                Files.createDirectories(JSON_PATH.getParent());
            }
            // categories의 내용을 Json 형식으로 json 에 할당
            String json = gson.toJson(categories);
            // json 의 내용을 JSON_PATH 에 저장
            Files.writeString(JSON_PATH, json);
        } catch (IOException e) {
            throw new RuntimeException("상품 데이터를 저장하는 중 오류가 발생했습니다.", e);
        }
    }

    public List<Category> load() {
        if(!Files.exists(JSON_PATH)) {
            return null;
        }

        try {
            // JSON_PATH 파일을 문자열로 읽어와서 json 에 할당
            String json = Files.readString(JSON_PATH);
            // TypeToken<List<Category>>: List<Category> 라는 구체적인 제네릭 타입 정보를 보존
            // getType(): Gson 이 사용할 수 있는 Type 객체 반환
            Type type = new TypeToken<List<Category>>() {}.getType();
            List<Category> categories = gson.fromJson(json, type);
            return categories;
        } catch (IOException e) {
            throw new RuntimeException("상품 데이터를 읽어오는 중 오류가 발생했습니다.", e);
        }
    }
}
