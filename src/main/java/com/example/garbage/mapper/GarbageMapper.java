package com.example.garbage.mapper;
import com.example.garbage.entity.*;
import org.apache.ibatis.annotations.*;
import java.util.List;
@Mapper
public interface GarbageMapper {
    @Select("select * from garbage_category order by id")
    List<GarbageCategory> listCategories();
    @Select("select * from garbage_category where id = #{id}")
    GarbageCategory findCategory(Integer id);
    @Select("<script>select g.*, c.name as category_name, c.color as category_color " +
            "from garbage_item g join garbage_category c on g.category_id = c.id <where>" +
            "<if test='keyword != null and keyword != \"\"'>and (g.name like concat('%',#{keyword},'%') or g.alias like concat('%',#{keyword},'%'))</if>" +
            "<if test='categoryId != null'>and g.category_id = #{categoryId}</if></where> order by g.id</script>")
    List<GarbageItem> searchItems(@Param("keyword") String keyword, @Param("categoryId") Integer categoryId);
    @Insert("insert into garbage_item(category_id,name,alias,hazard_level,decomposition_time,common_mistake,recycle_info,create_time) " +
            "values(#{categoryId},#{name},#{alias},#{hazardLevel},#{decompositionTime},#{commonMistake},#{recycleInfo},now())")
    int insertItem(GarbageItem item);
    @Delete("delete from garbage_item where id = #{id}")
    int deleteItem(Long id);
    @Select("select count(*) from garbage_item")
    int countItems();
}
