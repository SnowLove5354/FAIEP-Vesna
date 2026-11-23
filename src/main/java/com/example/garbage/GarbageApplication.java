package com.example.garbage;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
@MapperScan("com.example.garbage.mapper")
public class GarbageApplication {
    public static void main(String[] args) { SpringApplication.run(GarbageApplication.class, args); }
}
