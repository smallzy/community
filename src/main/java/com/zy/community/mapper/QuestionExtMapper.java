package com.zy.community.mapper;

import com.zy.community.pojo.Question;
import com.zy.community.pojo.QuestionExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface QuestionExtMapper {
    int updateViewCount(Question record);
    int updateCommentCount(Question record);
    List<Question> selectRelated(Question record);
}