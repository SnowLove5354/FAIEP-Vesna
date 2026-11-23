package com.example.garbage.service;
import com.example.garbage.entity.RecycleRecord;
import java.util.*;
public interface RecycleService {
    void submit(RecycleRecord record);
    List<RecycleRecord> listByUser(Long userId);
    List<RecycleRecord> listForAdmin(Integer status);
    void audit(Long id, int status, String reason);
    int countAll();
    List<Map<String, Object>> statsByCategory();
    List<Map<String, Object>> statsTrend();
}
