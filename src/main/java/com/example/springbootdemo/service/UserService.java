package com.example.springbootdemo.service;

import com.example.springbootdemo.dao.UserDao;
import com.example.springbootdemo.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Component
public class UserService {
    private final UserDao userDao;


    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }


    public List<UserVo> getAllUser(){
        return userDao.getAllUser();
    }



}
