package com.example.garbage.service;
import com.example.garbage.dto.QuizSubmitDto;
import com.example.garbage.entity.QuizQuestion;
import java.util.*;
public interface QuizService {
    List<QuizQuestion> randomQuestions(Integer categoryId, int num);
    Map<String, Object> submit(Long userId, QuizSubmitDto dto);
    List<QuizQuestion> listQuestions();
    void addQuestion(QuizQuestion question);
    void deleteQuestion(Long id);
    int countQuestions();
}
