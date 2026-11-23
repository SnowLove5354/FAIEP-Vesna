package com.example.garbage.service.impl;
import com.example.garbage.entity.Article;
import com.example.garbage.mapper.ArticleMapper;
import com.example.garbage.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired private ArticleMapper articleMapper;
    @Override public List<Article> list(String type, Integer status) { return articleMapper.list(type, status); }
    @Override public Article detail(Long id) { articleMapper.incView(id); return articleMapper.findById(id); }
    @Override public void add(Article article) {
        if (article.getTopFlag() == null) article.setTopFlag(0);
        articleMapper.insert(article);
    }
    @Override public void delete(Long id) { articleMapper.delete(id); }
}
