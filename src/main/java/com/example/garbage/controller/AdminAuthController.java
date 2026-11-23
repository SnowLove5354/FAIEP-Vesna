package com.example.garbage.controller;
import com.example.garbage.common.*;
import com.example.garbage.entity.User;
import com.example.garbage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
@Controller
public class AdminAuthController {
    @Autowired private UserService userService;
    @GetMapping("/admin/login") public String page() { return "admin/login"; }
    @PostMapping("/admin/login")
    public String login(String username, String password, Model model, HttpSession session) {
        try {
            User u = userService.login(username, password);
            if ("USER".equals(u.getRole())) throw new ServiceException("无后台管理权限");
            u.setPassword(null);
            session.setAttribute(Constants.SESSION_USER, u);
            return "redirect:/admin/index";
        } catch (ServiceException e) { model.addAttribute("error", e.getMessage()); return "admin/login"; }
    }
}
