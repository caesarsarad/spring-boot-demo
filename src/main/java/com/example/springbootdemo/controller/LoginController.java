package com.example.springbootdemo.controller;

import com.example.springbootdemo.entity.Math;
import com.example.springbootdemo.entity.User;
import com.example.springbootdemo.service.LoginService;
import com.example.springbootdemo.vo.MathVo;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
public class LoginController {
    @Autowired
    LoginService loginService;

    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/")
    public String login() {
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
    public String loginToList(String userName, String password, HttpServletRequest request,Model model) {

        User result =loginService.login(userName,password);
        if(result != null){
            HttpSession session = request.getSession();
            User user = new User();
            session.setAttribute("user",result);

            model.addAttribute("mathVo",new MathVo());


            PageInfo<Math> pageInfo = new PageInfo<>(new ArrayList<Math>());

            model.addAttribute("mathPageInfo",pageInfo);
            return "/study/mathlist.html";

        }else{
            return  "/login/login.html";
        }
    }

    @PostMapping("/logout")
    public String loginToList(HttpServletRequest request,Model model) {
        HttpSession session = request.getSession();
        session.setAttribute("user",null);
        return  "/login/login.html";
    }

//        @GetMapping("/login")
//    public String loginToList(Model model) {
//        model.addAttribute("bookForm",new MovieForm());
//            return "/study/test.html";
//}
}
