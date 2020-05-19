package com.zy.community.mapper;

import com.zy.community.pojo.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Select("select * from user where account_id=#{id}")
    User getUser(@Param("id") String id);

    @Insert("Insert into user (account_id,name,token,gmt_create,gmt_modified,bio,headimage_url) values (#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified},#{bio},#{headImageUrl})")
    void InsertGitHubUser(User user);

    @Select("select * from user where token=#{token}")
    User findUserByToken(@Param("token") String token);

    @Select("select * from user where id=#{id}")
    User findUserById(@Param("id") long id);

    @Update("update user set name=#{name},token=#{token},gmt_modified=#{gmtModified},bio=#{bio},headimage_url=#{headImageUrl} where account_id=#{accountId}")
    void updateTokenByUserId(User user);
}
