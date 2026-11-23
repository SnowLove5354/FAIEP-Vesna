package com.example.garbage.controller;
import com.example.garbage.entity.*;
import com.example.garbage.service.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.*;
import java.util.stream.Collectors;
@Controller
@RequestMapping("/admin/quiz")
public class AdminQuizController {
    @Autowired private QuizService quizService;
    @Autowired private GarbageService garbageService;
    @Autowired private ObjectMapper objectMapper;

    @GetMapping
    public String list(@RequestParam(required = false) Integer categoryId, Model model) {
        List<QuizQuestion> all = quizService.listQuestions();
        if (categoryId != null) {
            all = all.stream().filter(q -> categoryId.equals(q.getCategoryId())).collect(Collectors.toList());
        }
        List<GarbageCategory> categories = garbageService.listCategories();
        Map<Integer, String> catNameMap = new HashMap<>();
        for (GarbageCategory c : categories) catNameMap.put(c.getId(), c.getName());
        List<Map<String, Object>> rows = new ArrayList<>();
        for (QuizQuestion q : all) {
            Map<String, Object> m = new HashMap<>();
            m.put("id", q.getId());
            m.put("questionText", q.getQuestionText());
            m.put("correctAnswer", q.getCorrectAnswer());
            m.put("categoryId", q.getCategoryId());
            m.put("categoryName", q.getCategoryId() != null
                    ? catNameMap.getOrDefault(q.getCategoryId(), "未知") : "—");
            m.put("type", q.getType());
            List<String> displayOptions = new ArrayList<>();
            int correctIdx = -1;
            try {
                List<String> opts = objectMapper.readValue(
                        q.getOptionsJson(), new TypeReference<List<String>>() {});
                for (int i = 0; i < opts.size(); i++) {
                    char letter = (char) ('A' + i);
                    displayOptions.add(letter + ". " + opts.get(i));
                }
                if (q.getCorrectAnswer() != null && !q.getCorrectAnswer().isEmpty()) {
                    correctIdx = q.getCorrectAnswer().charAt(0) - 'A';
                    if (correctIdx < 0 || correctIdx >= opts.size()) correctIdx = -1;
                }
            } catch (Exception e) {
                displayOptions.add("（选项解析失败：" + e.getMessage() + "）");
            }
            m.put("options", displayOptions);
            m.put("correctIndex", correctIdx);
            rows.add(m);
        }
        model.addAttribute("rows", rows);
        model.addAttribute("categories", categories);
        model.addAttribute("categoryId", categoryId);
        return "admin/quiz";
    }

    @PostMapping("/add")
    public String add(@RequestParam String questionText,
                      @RequestParam String optionsText,
                      @RequestParam String correctAnswer,
                      @RequestParam(required = false) Integer categoryId,
                      @RequestParam(required = false) Integer filterCategoryId,
                      RedirectAttributes ra) {
        String backUrl = "redirect:/admin/quiz"
                + (filterCategoryId != null ? "?categoryId=" + filterCategoryId : "");
        if (questionText == null || questionText.trim().isEmpty()) {
            ra.addFlashAttribute("msg", "❌ 题干不能为空");
            return backUrl;
        }
        List<String> options = new ArrayList<>();
        for (String line : optionsText.split("\\r?\\n")) {
            String t = line.trim();
            if (!t.isEmpty()) options.add(t);
        }
        if (options.size() < 2) {
            ra.addFlashAttribute("msg", "❌ 至少需要 2 个选项（每行一个）");
            return backUrl;
        }
        if (options.size() > 8) {
            ra.addFlashAttribute("msg", "❌ 选项不能超过 8 个（A~H）");
            return backUrl;
        }
        if (optionsText.length() > 900) {
            ra.addFlashAttribute("msg", "❌ 选项内容过长，请精简");
            return backUrl;
        }
        String ans = correctAnswer == null ? "" : correctAnswer.trim().toUpperCase();
        if (ans.isEmpty() || ans.length() > 1) {
            ra.addFlashAttribute("msg", "❌ 请选择正确的答案（A~H）");
            return backUrl;
        }
        int idx = ans.charAt(0) - 'A';
        if (idx < 0 || idx >= options.size()) {
            ra.addFlashAttribute("msg", "❌ 正确答案超出选项范围（当前选项数：" + options.size() + "）");
            return backUrl;
        }
        try {
            QuizQuestion q = new QuizQuestion();
            q.setQuestionText(questionText.trim());
            q.setOptionsJson(objectMapper.writeValueAsString(options));
            q.setCorrectAnswer(ans);
            q.setCategoryId(categoryId);
            q.setType("SINGLE");
            quizService.addQuestion(q);
            ra.addFlashAttribute("msg", "✅ 题目添加成功");
        } catch (Exception e) {
            ra.addFlashAttribute("msg", "❌ 添加失败：" + e.getMessage());
        }
        return backUrl;
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id,
                         @RequestParam(required = false) Integer filterCategoryId,
                         RedirectAttributes ra) {
        quizService.deleteQuestion(id);
        ra.addFlashAttribute("msg", "✅ 题目已删除");
        return "redirect:/admin/quiz"
                + (filterCategoryId != null ? "?categoryId=" + filterCategoryId : "");
    }
}
