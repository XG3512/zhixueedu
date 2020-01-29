package com.xvls.alexander.service.impl;

import com.xvls.alexander.dao.UserMapper;
import com.xvls.alexander.entity.User;
import com.xvls.alexander.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User queryUserByName(String name) {
        return userMapper.queryUserByName(name);
    }
}
