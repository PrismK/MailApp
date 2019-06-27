package com.mailapp.bean;


public class UserData {

    private String userName;
    private String userPwd;

    public String getUserName() {             //获取用户名
        return userName;
    }

    public void setUserName(String userName) {  //输入用户名
        this.userName = userName;
    }

    public String getUserPwd() {                //获取用户密码
        return userPwd;
    }

    public void setUserPwd(String userPwd) {     //输入用户密码
        this.userPwd = userPwd;
    }

    public UserData(String userName, String userPwd) {
        super();
        this.userName = userName;
        this.userPwd = userPwd;
    }
}
