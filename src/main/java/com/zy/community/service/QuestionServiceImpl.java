package com.zy.community.service;

import com.zy.community.dto.PageNavigationDTO;
import com.zy.community.dto.QuestionDTO;
import com.zy.community.exception.CustomizeErrorCode;
import com.zy.community.exception.CustomizeException;
import com.zy.community.mapper.QuestionMapper;
import com.zy.community.mapper.UserMapper;
import com.zy.community.pojo.Question;
import com.zy.community.pojo.QuestionExample;
import com.zy.community.pojo.User;
import org.apache.ibatis.session.RowBounds;
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
        Question ques = questionMapper.selectByPrimaryKey(id);
        if(ques!=null){
            ques.setTitle(question.getTitle());
            ques.setDescription(question.getDescription());
            ques.setTag(question.getTag());
            ques.setGmtModified(System.currentTimeMillis());
            int i = questionMapper.updateByPrimaryKeySelective(ques);
            if(i!=1){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FIND);
            }
        }else{
            question.setCreator(user.getId());
            question.setLikeCount(0);
            question.setViewCount(0);
            question.setCommentCount(0);
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insert(question);
        }
    }

    @Override
    public PageNavigationDTO selectQuestionRecords(Integer page, Integer size) {

        //总行数
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria();
        Integer count = (int)questionMapper.countByExample(questionExample);

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

        questionExample.createCriteria();
        List<Question> questions = questionMapper.selectByExampleWithBLOBsWithRowbounds(questionExample, new RowBounds(offset, size));

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
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question==null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FIND);
        }
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        questionDTO.setUser(user);
        return questionDTO;
    }
}
