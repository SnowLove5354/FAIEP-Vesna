package com.example.garbage.service;
import com.example.garbage.entity.Article;
import java.util.List;
public interface ArticleService {
    List<Article> list(String type, Integer status);
    Article detail(Long id);
    void add(Article article);
    void delete(Long id);
}
