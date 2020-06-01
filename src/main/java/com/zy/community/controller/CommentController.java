package com.zy.community.controller;

import com.zy.community.dto.CommentDTO;
import com.zy.community.dto.ResultDTO;
import com.zy.community.exception.CustomizeErrorCode;
import com.zy.community.mapper.CommentMapper;
import com.zy.community.pojo.Comment;
import com.zy.community.pojo.User;
import com.zy.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
public class CommentController {
    @Autowired
    CommentService commentService;

    @PostMapping("/comment")
    @ResponseBody
    public Object insert(@RequestBody CommentDTO commentDTO,
                         HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        if (user == null){
            return ResultDTO.resultOf(CustomizeErrorCode.LOGIN_ERROR);
        }
        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentID());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setCommentator(user.getId());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        commentService.insertComment(comment);
        return ResultDTO.successCode();
    }
}
