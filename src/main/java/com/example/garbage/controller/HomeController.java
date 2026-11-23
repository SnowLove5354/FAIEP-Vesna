package com.example.garbage.controller;
import com.example.garbage.entity.GarbageCategory;
import com.example.garbage.entity.GarbageItem;
import com.example.garbage.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.*;
import java.util.stream.Collectors;
@Controller
public class HomeController {
    @Autowired private GarbageService garbageService;
    @Autowired private ArticleService articleService;
    @GetMapping("/") public String index(Model model) {
        model.addAttribute("categories", garbageService.listCategories());
        model.addAttribute("articles", articleService.list("ARTICLE", 1));
        model.addAttribute("notices", articleService.list("NOTICE", 1));
        return "front/index";
    }
    @GetMapping("/knowledge") public String knowledge(Model model) {
        List<GarbageItem> items = garbageService.search(null, null);
        Map<Integer, List<GarbageItem>> map = items.stream().collect(Collectors.groupingBy(GarbageItem::getCategoryId));
        model.addAttribute("categories", garbageService.listCategories());
        model.addAttribute("itemMap", map);
        return "front/knowledge";
    }
    @GetMapping("/category/{id}") public String category(@PathVariable Integer id, Model model) {
        GarbageCategory category = garbageService.findCategory(id);
        if (category == null) return "redirect:/knowledge";
        model.addAttribute("category", category);
        model.addAttribute("items", garbageService.search(null, id));
        return "front/category";
    }
    @GetMapping("/quiz") public String quiz() { return "front/quiz"; }
}
