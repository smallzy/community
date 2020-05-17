package com.zy.community.controller;

import com.zy.community.dto.PageNavigationDTO;
import com.zy.community.dto.QuestionDTO;
import com.zy.community.pojo.User;
import com.zy.community.service.QuestionService;
import com.zy.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private UserService userService;
    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model,
                        @RequestParam(value = "page",defaultValue = "1") Integer page,
                        @RequestParam(value = "size",defaultValue = "3") Integer size){
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    User user = userService.findUserByToken(cookie.getValue());
                    //如果用户存在将用户写入session
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }
        PageNavigationDTO pageNavigationDTO = questionService.selectQuestionRecords(page, size);
        model.addAttribute("pageNavigationDTO",pageNavigationDTO);
        return "index";
    }
}
