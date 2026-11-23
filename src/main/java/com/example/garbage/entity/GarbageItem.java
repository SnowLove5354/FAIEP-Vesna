package com.example.garbage.entity;
import lombok.Data;
import java.time.LocalDateTime;
@Data
public class GarbageItem {
    private Long id; private Integer categoryId; private String name; private String alias;
    private String hazardLevel; private String decompositionTime; private String commonMistake;
    private String recycleInfo; private LocalDateTime createTime;
    private String categoryName; private String categoryColor;
}
