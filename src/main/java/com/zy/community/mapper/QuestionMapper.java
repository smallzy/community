package com.zy.community.mapper;

import com.zy.community.pojo.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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

}
