package com.example.springbootdemo.entity;

public class User{
    private int userId;

    private String userName;

    private String password;

    private String userNameChs;

    private String userProfile;

    private int userGrade;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserNameChs() {
        return userNameChs;
    }

    public void setUserNameChs(String userNameChs) {
        this.userNameChs = userNameChs;
    }

    public String getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(String userProfile) {
        this.userProfile = userProfile;
    }

    public int getUserGrade() {
        return userGrade;
    }

    public void setUserGrade(int userGrade) {
        this.userGrade = userGrade;
    }
}
