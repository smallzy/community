package com.zy.community.service;

import com.zy.community.dto.CommentResult;
import com.zy.community.enums.CommentType;
import com.zy.community.exception.CustomizeErrorCode;
import com.zy.community.exception.CustomizeException;
import com.zy.community.mapper.CommentMapper;
import com.zy.community.mapper.QuestionExtMapper;
import com.zy.community.mapper.QuestionMapper;
import com.zy.community.mapper.UserMapper;
import com.zy.community.pojo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentMapper commentMapper;

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    QuestionExtMapper questionExtMapper;

    @Autowired
    UserMapper userMapper;

    @Override
    @Transactional
    public void insertComment(Comment comment) {
        if(comment.getParentId() == null || comment.getParentId()==0){
            throw  new CustomizeException(CustomizeErrorCode.PARENT_NOT_FIND);
        }
        if (comment.getType()==null || !CommentType.isExist(comment.getType()))
        {
            throw new CustomizeException(CustomizeErrorCode.TYPE_NOT_FIND);
        }
        if (comment.getType() == CommentType.COMMENT.getType()){
            //回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if(dbComment==null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FIND);
            }
            comment.setLikeCount(0L);
            commentMapper.insert(comment);
        }else{
            //回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question == null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FIND);
            }
            comment.setLikeCount(0L);
            commentMapper.insert(comment);
            //增加评论数
            question.setCommentCount(1);
            questionExtMapper.updateCommentCount(question);
        }
    }

    @Override
    public List<CommentResult> getCommentsById(Integer id,CommentType type) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andParentIdEqualTo(id).andTypeEqualTo(type.getType());
        commentExample.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(commentExample);

        if(comments.size() == 0){
            return new ArrayList<>();
        }

        //lamda表达式存储评论人主键
        Set<Integer> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());

        //查询评论人信息
        List<Integer> list = new ArrayList<>();
        list.addAll(commentators);
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdIn(list);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Integer, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        //将结果封装
        List<CommentResult> commentResults = comments.stream().map(comment -> {
            CommentResult commentResult = new CommentResult();
            BeanUtils.copyProperties(comment,commentResult);
            commentResult.setUser(userMap.get(comment.getCommentator()));
            return commentResult;
        }).collect(Collectors.toList());

        return commentResults;
    }

    @Override
    public Long getCommentCount(Integer id) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andParentIdEqualTo(id).andTypeEqualTo(2);
        long l = commentMapper.countByExample(commentExample);
        return l;
    }
}
