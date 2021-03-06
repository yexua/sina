package com.news.sina.service;

import com.news.sina.pojo.User;

public interface UserService {

    void addUser(String username, String password, int headpic);

    boolean checkPassword(String username, String password);

    boolean checkUserNameIllegal(String username);

    User getUserByUserName(String username);

    User getUserByUserId(Integer userid);

    void loginSuccess(String username);
}
