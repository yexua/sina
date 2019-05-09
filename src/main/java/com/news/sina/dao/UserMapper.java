package com.news.sina.dao;

import com.news.sina.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

    int deleteByPrimaryKey(Integer userid);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userid);

    User selectByUserName(@Param("username") String username);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}