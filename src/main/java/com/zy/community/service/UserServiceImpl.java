package com.zy.community.service;

import com.zy.community.dto.GithubUser;
import com.zy.community.mapper.UserMapper;
import com.zy.community.pojo.User;
import com.zy.community.pojo.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public String insertUser(GithubUser githubUser) {
        String token = UUID.randomUUID().toString();
        //查询数据库是否有此用户，存在则更新token
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(String.valueOf(githubUser.getId()));
        List<User> users = userMapper.selectByExample(userExample);
        if (users.size()!=0){
            User accountUser = new User();
            accountUser.setId(users.get(0).getId());
            accountUser.setName(githubUser.getName());
            accountUser.setToken(token);
            accountUser.setBio(githubUser.getBio());
            accountUser.setHeadimageUrl(githubUser.getAvatar_url());
            accountUser.setGmtModified(System.currentTimeMillis());
            userMapper.updateByPrimaryKeySelective(accountUser);
        }else{
            User user = new User();
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setName(githubUser.getName());
            user.setToken(token);
            user.setBio(githubUser.getBio());
            user.setHeadimageUrl(githubUser.getAvatar_url());
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }
        return token;
    }

    @Override
    public User findUserByToken(String token) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andTokenEqualTo(token);
        List<User> users = userMapper.selectByExample(userExample);
        if (users.size()!=0)
        {return users.get(0);}
        return null;
    }
}
