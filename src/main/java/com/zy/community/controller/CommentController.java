package com.zy.community.controller;

import com.zy.community.dto.CommentDTO;
import com.zy.community.dto.CommentResult;
import com.zy.community.dto.ResultDTO;
import com.zy.community.enums.CommentType;
import com.zy.community.exception.CustomizeErrorCode;
import com.zy.community.mapper.CommentMapper;
import com.zy.community.pojo.Comment;
import com.zy.community.pojo.User;
import com.zy.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

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
        if(commentDTO == null || StringUtils.isEmpty(commentDTO.getContent().trim())){
            return ResultDTO.resultOf(CustomizeErrorCode.COMMENT_IS_EMPTY);
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

    @GetMapping("/comment/{id}")
    @ResponseBody
    public ResultDTO<List> getComment(@PathVariable(name = "id") Integer id){
        List<CommentResult> comments = commentService.getCommentsById(id, CommentType.COMMENT);
        return  ResultDTO.successCode(comments);
    }
}
