package com.example.garbage.mapper;
import com.example.garbage.entity.*;
import org.apache.ibatis.annotations.*;
import java.util.List;
@Mapper
public interface QuizMapper {
    @Select("<script>select * from quiz_question <where>" +
            "<if test='categoryId != null'>and category_id = #{categoryId}</if></where> " +
            "order by rand() limit #{limit}</script>")
    List<QuizQuestion> randomQuestions(@Param("categoryId") Integer categoryId, @Param("limit") int limit);
    @Select("select * from quiz_question where id = #{id}")
    QuizQuestion findQuestion(Long id);
    @Select("select * from quiz_question order by id desc")
    List<QuizQuestion> listQuestions();
    @Insert("insert into quiz_question(question_text,options_json,correct_answer,category_id,type) " +
            "values(#{questionText},#{optionsJson},#{correctAnswer},#{categoryId},#{type})")
    int insertQuestion(QuizQuestion question);
    @Delete("delete from quiz_question where id = #{id}")
    int deleteQuestion(Long id);
    @Insert("insert into quiz_record(user_id,score,correct_count,create_time) values(#{userId},#{score},#{correctCount},now())")
    int insertRecord(QuizRecord record);
    @Select("select count(*) from quiz_question")
    int countQuestions();
}
