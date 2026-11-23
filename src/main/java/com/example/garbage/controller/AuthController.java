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
public class AuthController {
    @Autowired private UserService userService;
    @GetMapping("/login") public String loginPage() { return "front/login"; }
    @PostMapping("/login")
    public String login(String username, String password, Model model, HttpSession session) {
        try {
            User u = userService.login(username, password);
            u.setPassword(null);
            session.setAttribute(Constants.SESSION_USER, u);
            return "USER".equals(u.getRole()) ? "redirect:/" : "redirect:/admin/index";
        } catch (ServiceException e) { model.addAttribute("error", e.getMessage()); return "front/login"; }
    }
    @GetMapping("/register") public String registerPage() { return "front/register"; }
    @PostMapping("/register")
    public String register(String username, String password, String nickname, String phone, Model model) {
        try { userService.register(username, password, nickname, phone); model.addAttribute("msg", "注册成功，请登录"); return "front/login"; }
        catch (ServiceException e) { model.addAttribute("error", e.getMessage()); return "front/register"; }
    }
    @GetMapping("/logout") public String logout(HttpSession session) { session.invalidate(); return "redirect:/"; }
}
