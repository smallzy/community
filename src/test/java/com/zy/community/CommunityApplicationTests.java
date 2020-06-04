package com.zy.community;

import com.zy.community.dto.PageNavigationDTO;
import com.zy.community.dto.QuestionDTO;
import com.zy.community.mapper.QuestionExtMapper;
import com.zy.community.mapper.QuestionMapper;
import com.zy.community.mapper.UserMapper;
import com.zy.community.pojo.Comment;
import com.zy.community.pojo.Question;
import com.zy.community.pojo.QuestionExample;
import com.zy.community.pojo.User;
import com.zy.community.service.CommentService;
import com.zy.community.service.ProfileService;
import com.zy.community.service.QuestionService;
import org.apache.ibatis.session.RowBounds;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
class CommunityApplicationTests {

    @Autowired
    UserMapper userMapper;
    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    QuestionExtMapper questionExtMapper;

    @Autowired
    ProfileService profileService;

    @Autowired
    QuestionService questionService;

    @Autowired
    CommentService commentService;

    @Test
    void contextLoads() {
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(7);
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(questionExample, new RowBounds(1, 2));
        System.out.println(questions);
    }

    @Test
    void test(){
        Question question = new Question();
        question.setId(17);
        question.setDescription("1");
        question.setCreator(2);
        question.setTitle("1");
        question.setGmtCreate(1l);
        question.setGmtModified(2l);
        question.setTag("update");
        questionService.getRelateQuestion(question);
    }

}
