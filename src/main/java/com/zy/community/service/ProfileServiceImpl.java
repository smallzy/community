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
public class ProfileServiceImpl implements ProfileService{
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
    @Override
    public PageNavigationDTO selectQuestionById(Integer id, Integer page, Integer size) {
        PageNavigationDTO pageNavigationDTO = new PageNavigationDTO();

        //用户问题总数
        Integer count = questionMapper.selectCount(id);

        pageNavigationDTO.setPageNavigation(count,page,size);

        //偏移量
        if(page<1) page=1;
        if((count != 0)&&(page>pageNavigationDTO.getTotalPage())) page=pageNavigationDTO.getTotalPage();
        Integer offset = size * (page - 1);
        
        //用户问题详细信息
        List<Question> questions = questionMapper.selectById(id, offset, size);

        //查询用户信息
        User user = userMapper.selectByPrimaryKey(id);

        List<QuestionDTO> questionDTOS = new ArrayList<>();
        //将数据映射到数据传输层
        if (questions != null) {
            for (Question question : questions) {
                QuestionDTO questionDTO = new QuestionDTO();
                BeanUtils.copyProperties(question,questionDTO);
                questionDTO.setUser(user);
                questionDTOS.add(questionDTO);
            }
        }

        //封装分页数据
        pageNavigationDTO.setQuestionDTOS(questionDTOS);


        return pageNavigationDTO;
    }
}
