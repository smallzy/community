package com.zy.community.controller;

import com.zy.community.dto.CommentResult;
import com.zy.community.dto.QuestionDTO;
import com.zy.community.mapper.QuestionExtMapper;
import com.zy.community.pojo.Question;
import com.zy.community.service.CommentService;
import com.zy.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    QuestionService questionService;
    @Autowired
    QuestionExtMapper questionExtMapper;
    @Autowired
    CommentService commentService;

    @GetMapping("/question/{id}")
    public String goQuestion(@PathVariable("id") Integer id,
                             Model model){
        QuestionDTO questionDTO = questionService.getQuestionById(id);
        model.addAttribute("questionDTO",questionDTO);

        //阅读数
        if(questionDTO!=null){
            Question question = new Question();
            question.setId(questionDTO.getId());
            questionExtMapper.updateViewCount(question);
        }

        List<CommentResult> comments = commentService.getCommentsById(id);
        model.addAttribute("comments",comments);

        return "question";
    }
}
