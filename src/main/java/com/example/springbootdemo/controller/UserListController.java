package com.example.springbootdemo.controller;

import com.example.springbootdemo.service.UserService;
import com.example.springbootdemo.vo.UserVo;
import com.sun.deploy.net.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserListController {

  @Autowired
  public UserService userService;

  public void setUserService(UserService userService) {
    this.userService = userService;
  }

  /** ユーザー一覧画面を表示 */
  @GetMapping("/list")
  public String getUserList() {
    // ユーザー一覧画面を表示
    return "/user/list";

  }

  @RequestMapping("/search")
  public String search(Model model){
    List<UserVo> users = userService.getAllUser();

    model.addAttribute("userlist",users);
    return "/user/list";

  }

}