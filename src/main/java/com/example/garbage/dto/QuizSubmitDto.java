package com.example.garbage.dto;
import lombok.Data;
import java.util.List;
@Data
public class QuizSubmitDto {
    private List<Answer> answers;
    @Data public static class Answer { private Long questionId; private String answer; }
}
