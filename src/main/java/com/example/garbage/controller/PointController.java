package com.example.garbage.controller;
import com.example.garbage.common.Constants;
import com.example.garbage.entity.User;
import com.example.garbage.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpSession;
@Controller
public class PointController {
    @Autowired private PointLogMapper pointLogMapper;
    @Autowired private UserMapper userMapper;
    @GetMapping("/points")
    public String points(Model model, HttpSession session) {
        User u = (User) session.getAttribute(Constants.SESSION_USER);
        model.addAttribute("logs", pointLogMapper.listByUser(u.getId()));
        model.addAttribute("points", userMapper.findById(u.getId()).getPoints());
        return "front/points";
    }
}
