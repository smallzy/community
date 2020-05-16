package com.zy.community.mapper;

import com.zy.community.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("select * from user where id=#{id}")
    User getUser(@Param("id") Integer id);

    @Insert("Insert into user (account_id,name,token,gmt_create,gmt_modified,bio,headimage_url) values (#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified},#{bio},#{headImageUrl})")
    void InsertGitHubUser(User user);

    @Select("select * from user where token=#{token}")
    User findUserByToken(@Param("token") String token);

    @Select("select * from user where id=#{id}")
    User findUserById(@Param("id") long id);
}
