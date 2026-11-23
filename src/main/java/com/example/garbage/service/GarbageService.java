package com.example.garbage.service;
import com.example.garbage.entity.*;
import java.util.List;
public interface GarbageService {
    List<GarbageCategory> listCategories();
    GarbageCategory findCategory(Integer id);
    List<GarbageItem> search(String keyword, Integer categoryId);
    void addItem(GarbageItem item);
    void deleteItem(Long id);
    int countItems();
}
