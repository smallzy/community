package com.zy.community.service;

import com.zy.community.dto.QuestionDTO;
import com.zy.community.mapper.QuestionMapper;
import com.zy.community.mapper.UserMapper;
import com.zy.community.pojo.Question;
import com.zy.community.pojo.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public void insertQuestion(Question question, User user) {
            question.setCreator(user.getId());
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insertQuestion(question);
    }

    @Override
    public List<QuestionDTO> selectQuestionRecords() {
        List<Question> questions = questionMapper.selectAllQuestion();
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        if (questions != null){
            for (Question question : questions) {
                User user = userMapper.findUserById(question.getCreator());
                QuestionDTO questionDTO = new QuestionDTO();
                BeanUtils.copyProperties(question,questionDTO);
                questionDTO.setUser(user);
                questionDTOS.add(questionDTO);
            }
        }
        return questionDTOS;
    }
}
