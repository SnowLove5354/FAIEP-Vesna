package com.example.garbage.entity;
import lombok.Data;
import java.time.LocalDateTime;
@Data
public class QuizRecord {
    private Long id; private Long userId; private Integer score;
    private Integer correctCount; private LocalDateTime createTime;
}
