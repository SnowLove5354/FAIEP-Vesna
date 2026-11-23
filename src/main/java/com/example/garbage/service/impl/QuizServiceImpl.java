package com.example.garbage.service.impl;
import com.example.garbage.common.ServiceException;
import com.example.garbage.dto.QuizSubmitDto;
import com.example.garbage.entity.*;
import com.example.garbage.mapper.*;
import com.example.garbage.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
@Service
public class QuizServiceImpl implements QuizService {
    @Autowired private QuizMapper quizMapper;
    @Autowired private PointLogMapper pointLogMapper;
    @Autowired private UserMapper userMapper;
    @Override public List<QuizQuestion> randomQuestions(Integer categoryId, int num) { return quizMapper.randomQuestions(categoryId, num); }
    @Override @Transactional public Map<String, Object> submit(Long userId, QuizSubmitDto dto) {
        if (dto.getAnswers() == null || dto.getAnswers().isEmpty()) {
            throw new ServiceException("未提交任何答案");
        }
        int correct = 0;
        List<Map<String, Object>> details = new ArrayList<>();
        for (QuizSubmitDto.Answer a : dto.getAnswers()) {
            QuizQuestion q = quizMapper.findQuestion(a.getQuestionId());
            if (q == null) continue;
            boolean ok = q.getCorrectAnswer() != null && q.getCorrectAnswer().equalsIgnoreCase(a.getAnswer());
            if (ok) correct++;
            Map<String, Object> m = new HashMap<>();
            m.put("questionText", q.getQuestionText()); m.put("yourAnswer", a.getAnswer());
            m.put("correctAnswer", q.getCorrectAnswer()); m.put("ok", ok);
            details.add(m);
        }
        int score = correct * 2;
        QuizRecord record = new QuizRecord();
        record.setUserId(userId); record.setScore(score); record.setCorrectCount(correct);
        quizMapper.insertRecord(record);
        if (score > 0) {
            PointLog log = new PointLog();
            log.setUserId(userId); log.setChangePoints(score); log.setType("答题奖励");
            log.setRemark("答题正确 " + correct + " 题");
            pointLogMapper.insert(log);
            userMapper.addPoints(userId, score);
        }
        Map<String, Object> res = new HashMap<>();
        res.put("score", score); res.put("correctCount", correct); res.put("details", details);
        return res;
    }
    @Override public List<QuizQuestion> listQuestions() { return quizMapper.listQuestions(); }
    @Override public void addQuestion(QuizQuestion question) { quizMapper.insertQuestion(question); }
    @Override public void deleteQuestion(Long id) { quizMapper.deleteQuestion(id); }
    @Override public int countQuestions() { return quizMapper.countQuestions(); }
}
