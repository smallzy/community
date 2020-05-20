package com.zy.community.service;

import com.zy.community.dto.PageNavigationDTO;
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
    public void insertQuestion(Question question, User user, Integer id) {
        Question ques = questionMapper.selectQuestion(id);
        if(ques!=null){
            ques.setTitle(question.getTitle());
            ques.setDescription(question.getDescription());
            ques.setTag(question.getTag());
            ques.setGmtModified(System.currentTimeMillis());
            questionMapper.updateQuestion(ques);
        }else{
            question.setCreator(user.getId());
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insertQuestion(question);
        }
    }

    @Override
    public PageNavigationDTO selectQuestionRecords(Integer page, Integer size) {

        //总行数
        Integer count = questionMapper.selectAll();

        PageNavigationDTO pageNavigationDTO = new PageNavigationDTO();
        pageNavigationDTO.setPageNavigation(count,page,size);

        if (page<0){
            page=1;
        }
        if (page>pageNavigationDTO.getTotalPage()){
            page=pageNavigationDTO.getTotalPage();
        }
        //计算偏移量
        Integer offset = size*(page-1);

        List<Question> questions = questionMapper.selectAllQuestion(offset,size);

        List<QuestionDTO> questionDTOS = new ArrayList<>();
        if (questions != null){
            for (Question question : questions) {
                User user = userMapper.selectByPrimaryKey(question.getCreator());
                QuestionDTO questionDTO = new QuestionDTO();
                BeanUtils.copyProperties(question,questionDTO);
                questionDTO.setUser(user);
                questionDTOS.add(questionDTO);
            }
        }
        pageNavigationDTO.setQuestionDTOS(questionDTOS);

        return pageNavigationDTO;
    }

    @Override
    public QuestionDTO getQuestionById(Integer id) {
        Question question = questionMapper.selectQuestion(id);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        questionDTO.setUser(user);
        return questionDTO;
    }
}
