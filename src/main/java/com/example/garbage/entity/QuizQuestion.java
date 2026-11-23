package com.example.garbage.entity;
import lombok.Data;
@Data
public class QuizQuestion {
    private Long id; private String questionText; private String optionsJson;
    private String correctAnswer; private Integer categoryId; private String type;
}
