package com.wide.mapper;

import com.wide.bean.User;

public interface UserMapper {

    public User checkUserByNamePwd(String username, String password);

}
