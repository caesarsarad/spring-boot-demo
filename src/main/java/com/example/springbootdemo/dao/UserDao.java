package com.example.springbootdemo.dao;

import com.example.springbootdemo.mapper.UserMapper;
import com.example.springbootdemo.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class UserDao {
    public final UserMapper userMapper;

    public UserDao(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public List<UserVo> getAllUser(){
        return userMapper.getAllUser();
    }
}
