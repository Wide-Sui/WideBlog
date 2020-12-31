package com.wide.service;

import com.wide.Utils.MD5Utils;
import com.wide.bean.User;
import com.wide.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired(required = false)
    UserMapper userMapper;

    @Override
    public User checkUser(String username, String password) {
        User user = userMapper.checkUserByNamePwd(username, MD5Utils.code(password));
        return user;
    }
}
