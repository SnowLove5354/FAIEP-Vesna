package com.example.garbage.service;
import com.example.garbage.entity.User;
import java.util.List;
public interface UserService {
    User register(String username, String password, String nickname, String phone);
    User login(String username, String password);
    List<User> listUsers();
    void updateStatus(Long id, int status);
}
