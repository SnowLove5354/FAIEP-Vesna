package com.example.garbage.controller;
import com.example.garbage.entity.Article;
import com.example.garbage.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Controller
public class SearchController {
    @Autowired private GarbageService garbageService;
    @Autowired private ArticleService articleService;
    @GetMapping("/search") public String search(String keyword, Integer categoryId, Model model) {
        model.addAttribute("keyword", keyword);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("categories", garbageService.listCategories());
        model.addAttribute("items", garbageService.search(keyword, categoryId));
        return "front/search";
    }
    @GetMapping("/article/{id}")
    public String article(@PathVariable Long id, Model model) {
        Article a = articleService.detail(id);
        if (a == null) return "redirect:/";
        model.addAttribute("article", a);
        return "front/article";
    }
}
