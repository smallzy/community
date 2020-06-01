package com.zy.community.service;

import com.zy.community.enums.CommentType;
import com.zy.community.exception.CustomizeErrorCode;
import com.zy.community.exception.CustomizeException;
import com.zy.community.mapper.CommentMapper;
import com.zy.community.mapper.QuestionExtMapper;
import com.zy.community.mapper.QuestionMapper;
import com.zy.community.pojo.Comment;
import com.zy.community.pojo.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentMapper commentMapper;

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    QuestionExtMapper questionExtMapper;

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
            commentMapper.insert(comment);
        }else{
            //回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question == null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FIND);
            }
            commentMapper.insert(comment);
            //增加评论数
            question.setCommentCount(1);
            questionExtMapper.updateCommentCount(question);
        }
    }
}
