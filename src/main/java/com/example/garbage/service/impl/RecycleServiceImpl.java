package com.example.garbage.service.impl;
import com.example.garbage.common.*;
import com.example.garbage.entity.*;
import com.example.garbage.mapper.*;
import com.example.garbage.service.RecycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
@Service
public class RecycleServiceImpl implements RecycleService {
    @Autowired private RecycleRecordMapper recordMapper;
    @Autowired private PointLogMapper pointLogMapper;
    @Autowired private UserMapper userMapper;
    @Override public void submit(RecycleRecord record) {
        if (record.getCategoryId() == null) throw new ServiceException("请选择垃圾类别");
        if (record.getWeight() == null) record.setWeight(BigDecimal.ZERO);
        if (record.getQuantity() == null || record.getQuantity() < 1) record.setQuantity(1);
        String no = "R" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
                + String.format("%03d", new Random().nextInt(1000));
        record.setRecordNo(no);
        int pre = record.getWeight().multiply(new BigDecimal("2")).intValue();
        record.setPrePoints(Math.max(pre, 5));
        recordMapper.insert(record);
    }
    @Override public List<RecycleRecord> listByUser(Long userId) { return recordMapper.listByUser(userId); }
    @Override public List<RecycleRecord> listForAdmin(Integer status) { return recordMapper.listForAdmin(status); }
    @Override @Transactional public void audit(Long id, int status, String reason) {
        RecycleRecord r = recordMapper.findById(id);
        if (r == null) throw new ServiceException("记录不存在");
        if (status != Constants.RECORD_PASS && status != Constants.RECORD_REJECT) throw new ServiceException("非法审核状态");
        int rows = recordMapper.audit(id, status, reason);
        if (rows == 0) throw new ServiceException("该记录已被审核");
        if (status == Constants.RECORD_PASS) {
            PointLog log = new PointLog();
            log.setUserId(r.getUserId()); log.setChangePoints(r.getPrePoints());
            log.setType("投放奖励"); log.setRecordId(id);
            log.setRemark("投放记录审核通过，单号 " + r.getRecordNo());
            pointLogMapper.insert(log);
            userMapper.addPoints(r.getUserId(), r.getPrePoints());
        }
    }
    @Override public int countAll() { return recordMapper.countAll(); }
    @Override public List<Map<String, Object>> statsByCategory() { return recordMapper.statsByCategory(); }
    @Override public List<Map<String, Object>> statsTrend() { return recordMapper.statsTrend(); }
}
