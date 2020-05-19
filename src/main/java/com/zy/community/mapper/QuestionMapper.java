package com.zy.community.mapper;

import com.zy.community.pojo.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("Insert into question (title,description,gmt_create,gmt_modified,creator,comment_count,view_count,like_count,tag) values " +
            "(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{commentCount},#{viewCount},#{likeCount},#{tag})")
    void insertQuestion(Question question); //插入问题

    @Select("select * from question limit #{offset},#{rows}")
    List<Question> selectAllQuestion(@Param("offset") Integer offset,@Param("rows") Integer rows);//查询所有问题

    @Select("SELECT COUNT(*) FROM question")
    Integer selectAll();

    @Select("select * from question where creator=#{id} limit #{offset},#{rows}")
    List<Question> selectById(@Param("id") Integer id,@Param("offset") Integer offset,@Param("rows") Integer rows);

    @Select("select count(1) from question where creator=#{id}")
    Integer selectCount(@Param("id") Integer id);

    @Select("select * from question where id=#{id}")
    Question selectQuestion(@Param("id") Integer id);

    @Update("update question set title=#{title},description=#{description},gmt_modified=#{gmtModified},tag=#{tag} where id=#{id}")
    void updateQuestion(Question question);
}
