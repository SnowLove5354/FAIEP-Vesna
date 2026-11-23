package com.example.garbage.controller;
import com.example.garbage.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Controller
@RequestMapping("/admin")
public class AdminHomeController {
    @Autowired private UserService userService;
    @Autowired private RecycleService recycleService;
    @Autowired private GarbageService garbageService;
    @Autowired private QuizService quizService;
    @GetMapping("/index") public String dashboard(Model model) {
        model.addAttribute("userCount", userService.listUsers().size());
        model.addAttribute("recordCount", recycleService.countAll());
        model.addAttribute("itemCount", garbageService.countItems());
        model.addAttribute("questionCount", quizService.countQuestions());
        model.addAttribute("categoryStats", recycleService.statsByCategory());
        model.addAttribute("trendStats", recycleService.statsTrend());
        return "admin/dashboard";
    }
    @GetMapping("/users") public String users(Model model) {
        model.addAttribute("users", userService.listUsers());
        return "admin/user";
    }
    @PostMapping("/users/status") public String updateStatus(Long id, Integer status) {
        userService.updateStatus(id, status);
        return "redirect:/admin/users";
    }
}
