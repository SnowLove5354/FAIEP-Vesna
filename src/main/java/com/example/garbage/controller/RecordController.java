package com.example.garbage.controller;
import com.example.garbage.common.Constants;
import com.example.garbage.entity.*;
import com.example.garbage.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpSession;
@Controller
public class RecordController {
    @Autowired private RecycleService recycleService;
    @Autowired private GarbageService garbageService;
    @GetMapping("/record") public String page(Model model, HttpSession session) {
        User u = (User) session.getAttribute(Constants.SESSION_USER);
        model.addAttribute("records", recycleService.listByUser(u.getId()));
        model.addAttribute("categories", garbageService.listCategories());
        return "front/record";
    }
    @PostMapping("/record/submit") public String submit(RecycleRecord record, HttpSession session, RedirectAttributes ra) {
        User u = (User) session.getAttribute(Constants.SESSION_USER);
        record.setUserId(u.getId());
        recycleService.submit(record);
        ra.addFlashAttribute("msg", "投放记录已提交，等待审核");
        return "redirect:/record";
    }
}
