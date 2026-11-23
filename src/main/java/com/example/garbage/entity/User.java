package com.example.garbage.entity;
import lombok.Data;
import java.time.LocalDateTime;
@Data
public class User {
    private Long id; private String username; private String password;
    private String nickname; private String phone; private String avatar;
    private String role; private Integer points; private Integer status;
    private LocalDateTime createTime;
}
