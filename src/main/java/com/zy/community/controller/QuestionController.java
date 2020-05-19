package com.zy.community.controller;

import com.zy.community.dto.QuestionDTO;
import com.zy.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {
    @Autowired
    QuestionService questionService;

    @GetMapping("/question/{id}")
    public String goQuestion(@PathVariable("id") Integer id,
                             Model model){
        QuestionDTO questionDTO = questionService.getQuestionById(id);
        model.addAttribute("questionDTO",questionDTO);
        return "question";
    }
}
