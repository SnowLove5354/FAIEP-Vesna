package com.example.garbage.entity;
import lombok.Data;
import java.time.LocalDateTime;
@Data
public class PointLog {
    private Long id; private Long userId; private Integer changePoints;
    private String type; private Long recordId; private String remark;
    private LocalDateTime createTime;
}
