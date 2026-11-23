package com.example.garbage.controller;
import com.example.garbage.entity.GarbageItem;
import com.example.garbage.service.GarbageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Controller
@RequestMapping("/admin/garbage")
public class AdminGarbageController {
    @Autowired private GarbageService garbageService;
    @GetMapping public String list(Model model) {
        model.addAttribute("items", garbageService.search(null, null));
        model.addAttribute("categories", garbageService.listCategories());
        return "admin/garbage";
    }
    @PostMapping("/add") public String add(GarbageItem item) { garbageService.addItem(item); return "redirect:/admin/garbage"; }
    @PostMapping("/delete/{id}") public String delete(@PathVariable Long id) { garbageService.deleteItem(id); return "redirect:/admin/garbage"; }
}
