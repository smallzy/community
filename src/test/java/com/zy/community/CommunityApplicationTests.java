package com.zy.community;

import com.zy.community.dto.PageNavigationDTO;
import com.zy.community.dto.QuestionDTO;
import com.zy.community.mapper.UserMapper;
import com.zy.community.pojo.User;
import com.zy.community.service.ProfileService;
import com.zy.community.service.QuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CommunityApplicationTests {

    @Autowired
    UserMapper userMapper;

    @Autowired
    ProfileService profileService;

    @Autowired
    QuestionService questionService;

    @Test
    void contextLoads() {
        QuestionDTO questionById = questionService.getQuestionById(1);
        System.out.println(questionById);
    }

    @Test
    void test(){
        PageNavigationDTO pageNavigationDTO = profileService.selectQuestionById(5, 2, 1);
        System.out.println(pageNavigationDTO);
    }

}
