package com.zy.community.service;


import com.zy.community.dto.CommentResult;
import com.zy.community.enums.CommentType;
import com.zy.community.pojo.Comment;

import java.util.List;

public interface CommentService {
    void insertComment(Comment comment);

    List<CommentResult> getCommentsById(Integer id, CommentType type);

    Long getCommentCount(Integer id);
}
