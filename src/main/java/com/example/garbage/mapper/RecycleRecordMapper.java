package com.example.garbage.mapper;
import com.example.garbage.entity.RecycleRecord;
import org.apache.ibatis.annotations.*;
import java.util.*;
@Mapper
public interface RecycleRecordMapper {
    @Insert("insert into recycle_record(record_no,user_id,category_id,`weight`,quantity,images,status,pre_points,create_time,update_time) " +
            "values(#{recordNo},#{userId},#{categoryId},#{weight},#{quantity},#{images},0,#{prePoints},now(),now())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(RecycleRecord record);
    @Select("select r.*, u.nickname as user_name, c.name as category_name, c.color as category_color " +
            "from recycle_record r join sys_user u on r.user_id = u.id " +
            "join garbage_category c on r.category_id = c.id where r.id = #{id}")
    RecycleRecord findById(Long id);
    @Select("select r.*, c.name as category_name, c.color as category_color " +
            "from recycle_record r join garbage_category c on r.category_id = c.id " +
            "where r.user_id = #{userId} order by r.id desc")
    List<RecycleRecord> listByUser(Long userId);
    @Select("<script>select r.*, u.nickname as user_name, c.name as category_name " +
            "from recycle_record r join sys_user u on r.user_id = u.id " +
            "join garbage_category c on r.category_id = c.id <where>" +
            "<if test='status != null'>and r.status = #{status}</if></where> order by r.id desc</script>")
    List<RecycleRecord> listForAdmin(@Param("status") Integer status);
    @Update("update recycle_record set status = #{status}, reject_reason = #{reason}, update_time = now() " +
            "where id = #{id} and status = 0")
    int audit(@Param("id") Long id, @Param("status") int status, @Param("reason") String reason);
    @Select("select count(*) from recycle_record")
    int countAll();
    @Select("select c.name as name, count(*) as value from recycle_record r " +
            "join garbage_category c on r.category_id = c.id group by r.category_id")
    List<Map<String, Object>> statsByCategory();
    @Select("select date(create_time) as day, count(*) as cnt from recycle_record " +
            "where create_time >= date_sub(curdate(), interval 6 day) group by date(create_time) order by day")
    List<Map<String, Object>> statsTrend();
}
