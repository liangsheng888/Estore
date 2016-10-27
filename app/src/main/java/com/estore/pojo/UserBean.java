package com.estore.pojo;

import java.io.Serializable;

/**
 * {
 "userId": 2,
 "email": "491830643@qq.com",
 "password": "123456",
 "nickname": "水封天",
 "active": 0,
 "userPhoto": "/images/fu_01.png",
 "user_address": "江苏省苏州市吴中区独墅湖高教区仁爱路一号",
 "user_phone": "120",
 "userSex": "1"
 }
 */
public class UserBean implements Serializable{
    private Integer userId;
    private String email;

//    public UserBean(String nickname, String user_address, String user_phone, Integer userId, String userSex) {
//        this.nickname = nickname;
//        this.user_address = user_address;
//        this.user_phone = user_phone;
//        this.userId = userId;
//        this.userSex = userSex;
//    }

    @Override
    public String toString() {
        return "UserBean{" +
                "email='" + email + '\'' +
                ", nickname='" + nickname + '\'' +
                ", user_address='" + user_address + '\'' +
                ", user_phone='" + user_phone + '\'' +
                ", userId=" + userId +
                ", userSex='" + userSex + '\'' +
                '}';
    }

    private String password;
    private String nickname;
    private String active;
    private String userPhoto;
    private String user_address;
    private String	user_phone;
    private String userSex;
    public UserBean(){}


    //修改有头像
    public UserBean( String nickname, String userSex, String user_address,Integer userId,String userPhoto ,String user_phone) {

        this.nickname = nickname;
        this.userSex = userSex;
        this.user_address = user_address;
        this.userId =userId;
        this.userPhoto =userPhoto;
        this.user_phone = user_phone;
    }
    //修改无头像
    public UserBean( String nickname, String userSex, String user_address,Integer userId,String user_phone) {

        this.nickname = nickname;
        this.userSex = userSex;
        this.user_address = user_address;
        this.userId =userId;
        this.user_phone = user_phone;
    }
    //查找
    public UserBean(String nickname, String userSex, String user_address,  String user_phone, String userPhoto) {

        this.nickname = nickname;
        this.userSex = userSex;
        this.user_address = user_address;
        this.user_phone = user_phone;
        this.userPhoto = userPhoto;
    }
    //我的主页头像和用户名
    public UserBean(String userPhoto,  String nickname) {
        this.nickname = nickname;
        this.userPhoto = userPhoto;
    }


    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser_address() {
        return user_address;
    }

    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }
}

