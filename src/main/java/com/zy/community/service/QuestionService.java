package com.zy.community.service;

import com.zy.community.dto.QuestionDTO;
import com.zy.community.pojo.Question;
import com.zy.community.pojo.User;

import java.util.List;

public interface QuestionService {
    void insertQuestion(Question question, User user); //插入问题
    List<QuestionDTO> selectQuestionRecords();//查询所有的问题
}
