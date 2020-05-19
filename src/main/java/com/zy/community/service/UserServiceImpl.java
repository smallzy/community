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
    public String insertUser(GithubUser githubUser) {
        String token = UUID.randomUUID().toString();
        //查询数据库是否有此用户，存在则更新token
        User accountUser = userMapper.getUser(String.valueOf(githubUser.getId()));
        if (accountUser!=null){
            accountUser.setName(githubUser.getName());
            accountUser.setToken(token);
            accountUser.setBio(githubUser.getBio());
            accountUser.setHeadImageUrl(githubUser.getAvatar_url());
            accountUser.setGmtModified(System.currentTimeMillis());
            userMapper.updateTokenByUserId(accountUser);
        }else{
            User user = new User();
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setName(githubUser.getName());
            user.setToken(token);
            user.setBio(githubUser.getBio());
            user.setHeadImageUrl(githubUser.getAvatar_url());
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.InsertGitHubUser(user);}
        return token;
    }

    @Override
    public User findUserByToken(String token) {
        User userByToken = userMapper.findUserByToken(token);
        return userByToken;
    }
}
