package com.example.garbage.entity;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
public class RecycleRecord {
    private Long id; private String recordNo; private Long userId; private Integer categoryId;
    private BigDecimal weight; private Integer quantity; private String images;
    private Integer status; private String rejectReason; private Integer prePoints;
    private LocalDateTime createTime; private LocalDateTime updateTime;
    private String userName; private String categoryName; private String categoryColor;
}
