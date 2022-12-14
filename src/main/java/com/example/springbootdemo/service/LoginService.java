package com.example.springbootdemo.service;

import com.example.springbootdemo.dao.LoginDao;
import com.example.springbootdemo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Component
@Service
public class LoginService {

    LoginDao loginDao;

    public LoginService(LoginDao loginDao) {
        this.loginDao = loginDao;
    }


    public User login(String userName, String password){
        if(userName == null || password == null){
            return  null;
        }
        String passwordMd5 = DigestUtils.md5DigestAsHex(password.getBytes());

        return loginDao.findUserByNamePass(userName,passwordMd5);
    }
}
