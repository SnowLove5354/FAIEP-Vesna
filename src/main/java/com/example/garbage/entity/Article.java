package com.example.garbage.entity;
import lombok.Data;
import java.time.LocalDateTime;
@Data
public class Article {
    private Long id; private String title; private String content; private String cover;
    private String type; private Integer viewCount; private Integer topFlag;
    private Integer status; private LocalDateTime createTime;
}
