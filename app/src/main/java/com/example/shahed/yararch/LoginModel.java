package com.example.shahed.yararch;

public class LoginModel {
    private int loginId;
    private String fullName;
    private String userName;
    private String password;
    private int userType;

    public LoginModel(String fullName, String userName, String password, int userType) {
        this.fullName = fullName;
        this.userName = userName;
        this.password = password;
        this.userType = userType;
    }

    public LoginModel(int loginId, String userName, String password, int userType) {
        this.loginId = loginId;
        this.userName = userName;
        this.password = password;
        this.userType = userType;
    }

    public LoginModel(String userName, String password, int userType) {
        this.userName = userName;
        this.password = password;
        this.userType = userType;
    }

    public LoginModel(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public int getLoginId() {
        return loginId;
    }

    public void setLoginId(int loginId) {
        this.loginId = loginId;
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

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
