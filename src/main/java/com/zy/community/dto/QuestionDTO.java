package com.zy.community.dto;

import com.zy.community.pojo.Question;
import com.zy.community.pojo.User;
import lombok.Data;

@Data
public class QuestionDTO extends Question {
    private User user;
}
