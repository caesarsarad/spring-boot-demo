package com.example.springbootdemo.dao;

import com.example.springbootdemo.entity.User;
import com.example.springbootdemo.mapper.LoginMapper;
import org.springframework.stereotype.Component;

@Component
public class LoginDao {
    LoginMapper loginMapper;

    public LoginDao(LoginMapper loginMapper) {
        this.loginMapper = loginMapper;
    }

    public User findUserByNamePass(String userName, String password){
        return loginMapper.findUserByNamePass(userName,password);
    }
}
