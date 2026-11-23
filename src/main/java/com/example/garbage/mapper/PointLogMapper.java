package com.example.garbage.mapper;
import com.example.garbage.entity.PointLog;
import org.apache.ibatis.annotations.*;
import java.util.List;
@Mapper
public interface PointLogMapper {
    @Insert("insert into point_log(user_id,change_points,type,record_id,remark,create_time) " +
            "values(#{userId},#{changePoints},#{type},#{recordId},#{remark},now())")
    int insert(PointLog log);
    @Select("select * from point_log where user_id = #{userId} order by id desc")
    List<PointLog> listByUser(Long userId);
}
