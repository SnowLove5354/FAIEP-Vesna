package com.example.garbage.entity;
import lombok.Data;
@Data
public class GarbageCategory {
    private Integer id; private String name; private String code;
    private String iconPath; private String color; private String disposalMethod;
}
