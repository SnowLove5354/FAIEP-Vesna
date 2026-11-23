package com.example.garbage.service.impl;
import com.example.garbage.entity.*;
import com.example.garbage.mapper.GarbageMapper;
import com.example.garbage.service.GarbageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class GarbageServiceImpl implements GarbageService {
    @Autowired private GarbageMapper garbageMapper;
    @Override public List<GarbageCategory> listCategories() { return garbageMapper.listCategories(); }
    @Override public GarbageCategory findCategory(Integer id) { return garbageMapper.findCategory(id); }
    @Override public List<GarbageItem> search(String keyword, Integer categoryId) { return garbageMapper.searchItems(keyword, categoryId); }
    @Override public void addItem(GarbageItem item) { garbageMapper.insertItem(item); }
    @Override public void deleteItem(Long id) { garbageMapper.deleteItem(id); }
    @Override public int countItems() { return garbageMapper.countItems(); }
}
