package com.st.fly.service.impl;

import com.st.fly.dao.UserDao;
import com.st.fly.entity.User;
import com.st.fly.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User login(String username, String password) throws Exception {
        //要求用户名和密码的长度都大于3
        if (username.length() < 3 || password.length() < 3) {
            throw new Exception("账号和密码不能少于3位");
        }
        return userDao.select(username, password);
    }

    @Override
    public int reg(User user, String repassword) {
        //两次密码是否一致
        if (!user.getPassword().equals(repassword)) {
            return -1;
        }
        //账号、密码、昵称的长度
        if (user.getUsername().length() < 3 ||
                user.getPassword().length() < 3 ||
                user.getNickname().length() < 2) {
            return -2;
        }
        //账号是否可用
        if (userDao.selectByUsername(user.getUsername()) != null) {
            return -3;
        }
        //昵称是否可用
        if (userDao.selectByNickname(user.getNickname()) != null) {
            return -4;
        }
        //保存用户信息
        return userDao.insert(user);
    }

    @Override
    public User getById(Integer uid) {
        return userDao.selectById(uid);
    }

    @Override
    public void set(User user) {
        userDao.update(user);
    }

}
