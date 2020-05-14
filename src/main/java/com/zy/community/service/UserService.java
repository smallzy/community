package com.zy.community.service;

import com.zy.community.dto.GithubUser;
import com.zy.community.pojo.User;


public interface UserService {
    String insertUser(GithubUser user); //插入用户
    User findUserByToken(String token);//通过token验证用户
}
