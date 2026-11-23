package com.example.garbage.mapper;
import com.example.garbage.entity.Article;
import org.apache.ibatis.annotations.*;
import java.util.List;
@Mapper
public interface ArticleMapper {
    @Select("<script>select * from article <where>" +
            "<if test='type != null'>and type = #{type}</if>" +
            "<if test='status != null'>and status = #{status}</if></where> " +
            "order by top_flag desc, id desc</script>")
    List<Article> list(@Param("type") String type, @Param("status") Integer status);
    @Select("select * from article where id = #{id}")
    Article findById(Long id);
    @Update("update article set view_count = view_count + 1 where id = #{id}")
    int incView(Long id);
    @Insert("insert into article(title,content,type,view_count,top_flag,status,create_time) " +
            "values(#{title},#{content},#{type},0,#{topFlag},1,now())")
    int insert(Article article);
    @Delete("delete from article where id = #{id}")
    int delete(Long id);
}
