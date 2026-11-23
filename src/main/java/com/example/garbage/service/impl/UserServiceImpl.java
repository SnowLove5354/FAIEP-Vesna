package com.example.garbage.service.impl;
import com.example.garbage.common.ServiceException;
import com.example.garbage.entity.User;
import com.example.garbage.mapper.UserMapper;
import com.example.garbage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    @Autowired private UserMapper userMapper;
    @Autowired private BCryptPasswordEncoder encoder;
    @Override public User register(String username, String password, String nickname, String phone) {
        if (username == null || username.trim().isEmpty()) throw new ServiceException("用户名不能为空");
        if (password == null || password.length() < 6) throw new ServiceException("密码长度不能少于 6 位");
        username = username.trim();
        if (userMapper.findByUsername(username) != null) throw new ServiceException("用户名已存在");
        User u = new User();
        u.setUsername(username); u.setPassword(encoder.encode(password));
        u.setNickname(nickname == null || nickname.isEmpty() ? username : nickname);
        u.setPhone(phone); u.setRole("USER"); u.setPoints(0); u.setStatus(1);
        userMapper.insert(u); return u;
    }
    @Override public User login(String username, String password) {
        User u = userMapper.findByUsername(username);
        if (u == null || !encoder.matches(password, u.getPassword())) throw new ServiceException("用户名或密码错误");
        if (u.getStatus() != null && u.getStatus() == 0) throw new ServiceException("账号已被禁用");
        return u;
    }
    @Override public List<User> listUsers() { return userMapper.listUsers(); }
    @Override public void updateStatus(Long id, int status) { userMapper.updateStatus(id, status); }
}
