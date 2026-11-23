package com.example.garbage.mapper;
import com.example.garbage.entity.User;
import org.apache.ibatis.annotations.*;
import java.util.List;
@Mapper
public interface UserMapper {
    @Select("select * from sys_user where username = #{username}")
    User findByUsername(String username);
    @Select("select * from sys_user where id = #{id}")
    User findById(Long id);
    @Insert("insert into sys_user(username,password,nickname,phone,role,points,status,create_time) " +
            "values(#{username},#{password},#{nickname},#{phone},#{role},#{points},#{status},now())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);
    @Update("update sys_user set points = points + #{delta} where id = #{id}")
    int addPoints(@Param("id") Long id, @Param("delta") int delta);
    @Select("select * from sys_user where role = 'USER' order by id desc")
    List<User> listUsers();
    @Update("update sys_user set status = #{status} where id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") int status);
}
