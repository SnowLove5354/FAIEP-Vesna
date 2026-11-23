package com.example.garbage.controller;
import com.example.garbage.entity.Article;
import com.example.garbage.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Controller
@RequestMapping("/admin/articles")
public class AdminArticleController {
    @Autowired private ArticleService articleService;
    @GetMapping public String list(Model model) {
        model.addAttribute("articles", articleService.list(null, null));
        return "admin/article";
    }
    @PostMapping("/add") public String add(Article article) { articleService.add(article); return "redirect:/admin/articles"; }
    @PostMapping("/delete/{id}") public String delete(@PathVariable Long id) { articleService.delete(id); return "redirect:/admin/articles"; }
}
