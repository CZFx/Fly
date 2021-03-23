package com.st.fly.service;

import com.st.fly.entity.User;

public interface UserService {

    //用户登录
    User login(String username, String password) throws Exception;

    //用户注册
    int reg(User user, String repassword);

    User getById(Integer uid);

    void set(User user);
}
