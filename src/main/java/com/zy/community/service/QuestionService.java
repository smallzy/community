package com.zy.community.service;

import com.zy.community.dto.PageNavigationDTO;
import com.zy.community.dto.QuestionDTO;
import com.zy.community.pojo.Question;
import com.zy.community.pojo.User;

import java.util.List;

public interface QuestionService {
    void insertQuestion(Question question, User user); //插入问题
    PageNavigationDTO selectQuestionRecords(Integer page, Integer size);//查询所有的问题
}
