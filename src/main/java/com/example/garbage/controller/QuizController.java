package com.example.garbage.controller;
import com.example.garbage.common.*;
import com.example.garbage.dto.QuizSubmitDto;
import com.example.garbage.entity.*;
import com.example.garbage.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.List;
@RestController
@RequestMapping("/quiz")
public class QuizController {
    @Autowired private QuizService quizService;
    @GetMapping("/questions")
    public Result questions(@RequestParam(defaultValue = "5") int num,
                            @RequestParam(required = false) Integer categoryId) {
        List<QuizQuestion> list = quizService.randomQuestions(categoryId, num);
        list.forEach(q -> q.setCorrectAnswer(null));
        return Result.ok(list);
    }
    @PostMapping("/submit")
    public Result submit(@RequestBody QuizSubmitDto dto, HttpSession session) {
        User u = (User) session.getAttribute(Constants.SESSION_USER);
        return Result.ok(quizService.submit(u.getId(), dto));
    }
}
