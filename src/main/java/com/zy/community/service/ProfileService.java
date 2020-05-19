package com.zy.community.service;

import com.zy.community.dto.PageNavigationDTO;

public interface ProfileService {
    PageNavigationDTO selectQuestionById(Integer id, Integer page, Integer size);//根据用户ID查询问题
}
