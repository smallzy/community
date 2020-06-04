package com.zy.community.controller;

import com.zy.community.cache.TagCache;
import com.zy.community.dto.QuestionDTO;
import com.zy.community.pojo.Question;
import com.zy.community.pojo.User;
import com.zy.community.service.QuestionService;
import com.zy.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
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
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }

    @GetMapping("/publish")
    public String goToPublish(Model model){
        model.addAttribute("tags", TagCache.get());
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
        model.addAttribute("tags", TagCache.get());

        User user = (User) request.getSession().getAttribute("user");

        if (StringUtils.isEmpty(title)) {
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if (StringUtils.isEmpty(description)) {
            model.addAttribute("error", "问题补充不能为空");
            return "publish";
        }
        if (StringUtils.isEmpty(tag)) {
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }

        String invalid = TagCache.filterInvalid(tag);
        if (!StringUtils.isEmpty(invalid)) {
            model.addAttribute("error", "输入非法标签:" + invalid);
            return "publish";
        }

        if(user == null){
            model.addAttribute("error","用户未登录！");
            return "publish";
        }

        if (user != null) {
            Question question = new Question();
            question.setTitle(title);
            question.setDescription(description);
            question.setTag(tag);
            questionService.insertQuestion(question,user,id);
        }

        return "redirect:/";
    }
}
