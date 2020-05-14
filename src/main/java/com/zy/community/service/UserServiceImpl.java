package com.zy.community.service;

import com.zy.community.dto.GithubUser;
import com.zy.community.mapper.UserMapper;
import com.zy.community.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public void insertUser(GithubUser githubUser) {
        User user = new User();
        user.setAccountId(String.valueOf(githubUser.getId()));
        user.setName(githubUser.getName());
        user.setToken(UUID.randomUUID().toString());
        user.setGmtCreate(System.currentTimeMillis());
        user.setGmtModified(user.getGmtCreate());
        userMapper.InserUser(user);
    }
}
