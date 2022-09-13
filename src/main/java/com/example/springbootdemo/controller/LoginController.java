package com.example.springbootdemo.controller;

import com.example.springbootdemo.entity.User;
import com.example.springbootdemo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    @Autowired
    LoginService loginService;

    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/")
    public String login(){
        return "/login/login.html";
    }
//    @GetMapping("/login")
//    public String register( String userName, String password){
//
//        return "/login/login.html";
////        int result =loginService.login(userName,password);
////        if(result > 0){
////            return "/study/list";
////        }else{
////            return  "fail";
////        }
//
//    }

    @GetMapping("/login")
    public String loginToList(String userName, String password, HttpServletRequest request) {

        int result =loginService.login(userName,password);
        if(result > 0){
            HttpSession session = request.getSession();
            User user = new User();
            session.setAttribute("user",user);
            return "/study/mathlist.html";
        }else{
            return  "/login/login.html";
        }
    }
}
