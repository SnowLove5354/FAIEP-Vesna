package com.example.garbage.controller;
import com.example.garbage.service.RecycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Controller
@RequestMapping("/admin/records")
public class AdminRecordController {
    @Autowired private RecycleService recycleService;
    @GetMapping public String list(@RequestParam(required = false) Integer status, Model model) {
        model.addAttribute("records", recycleService.listForAdmin(status));
        model.addAttribute("status", status);
        return "admin/record";
    }
    @PostMapping("/audit") public String audit(Long id, Integer status, String reason) {
        recycleService.audit(id, status, reason);
        return "redirect:/admin/records";
    }
}
