package com.zy.community.controller;

import com.zy.community.dto.PageNavigationDTO;
import com.zy.community.pojo.User;
import com.zy.community.service.ProfileService;
import com.zy.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          @RequestParam(value = "page",defaultValue = "1") Integer page,
                          @RequestParam(value = "size",defaultValue = "3") Integer size,
                          Model model,
                          HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        if (user == null){
            return "redirect:/";
        }
        PageNavigationDTO pageNavigationDTO = profileService.selectQuestionById(user.getId(), page, size);

        if (action.equals("questions")){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
            model.addAttribute("pageNavigationDTO",pageNavigationDTO);
        }else if(action.equals("replies")){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
        }
        return "profile";
    }
}
