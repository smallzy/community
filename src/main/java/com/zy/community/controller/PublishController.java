package com.zy.community.controller;

import com.zy.community.dto.QuestionDTO;
import com.zy.community.pojo.Question;
import com.zy.community.pojo.User;
import com.zy.community.service.QuestionService;
import com.zy.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish/{id}")
    public String updatePublic(@PathVariable Integer id,
                               Model model){
        QuestionDTO questionDTO = questionService.getQuestionById(id);
        model.addAttribute("title",questionDTO.getTitle());
        model.addAttribute("description",questionDTO.getDescription());
        model.addAttribute("tag",questionDTO.getTag());
        model.addAttribute("id",id);
        return "publish";
    }

    @GetMapping("/publish")
    public String goToPublish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(@RequestParam("title") String title,
                            @RequestParam("description") String description,
                            @RequestParam("tag") String tag,
                            HttpServletRequest request,
                            Model model,
                            @RequestParam(name = "id",required = false) Integer id){
        //数据回显
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);

        User user = (User) request.getSession().getAttribute("user");

        if (user != null) {
            Question question = new Question();
            question.setTitle(title);
            question.setDescription(description);
            question.setTag(tag);
            questionService.insertQuestion(question,user,id);
        }

        if(user == null){
            model.addAttribute("error","用户未登录！");
            return "publish";
        }
        return "redirect:/";
    }
}
