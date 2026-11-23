package com.example.garbage.init;
import com.example.garbage.entity.User;
import com.example.garbage.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired private UserMapper userMapper;
    @Autowired private BCryptPasswordEncoder encoder;
    @Override public void run(String... args) {
        createIfAbsent("admin", "123456", "系统管理员", "SYS_ADMIN");
        createIfAbsent("content", "123456", "内容管理员", "CONTENT_ADMIN");
        createIfAbsent("user1", "123456", "测试居民", "USER");
    }
    private void createIfAbsent(String username, String pwd, String nickname, String role) {
        if (userMapper.findByUsername(username) == null) {
            User u = new User();
            u.setUsername(username); u.setPassword(encoder.encode(pwd));
            u.setNickname(nickname); u.setRole(role); u.setPoints(0); u.setStatus(1);
            userMapper.insert(u);
        }
    }
}
