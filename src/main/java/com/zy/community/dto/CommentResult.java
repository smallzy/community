package com.zy.community.dto;

import com.zy.community.pojo.Comment;
import com.zy.community.pojo.User;
import lombok.Data;

@Data
public class CommentResult extends Comment {
    private long commentNum;
    private User user;
}
