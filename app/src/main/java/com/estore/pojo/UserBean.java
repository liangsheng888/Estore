package com.estore.pojo;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/10/21 0021.
 */
public class UserBean implements Serializable{
    private Integer userId;
    private String email;
    private String password;
    private String nickname;
    private String role;
    private String sex;
    private String user_photo;
    private String	user_phone;
    private String user_address;
    public UserBean(){}

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUser_photo() {
        return user_photo;
    }

    public void setUser_photo(String user_photo) {
        this.user_photo = user_photo;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getUser_address() {
        return user_address;
    }

    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }

    //修改
    public UserBean( String nickname, String sex, String user_address, Integer userId, String user_phone) {

        this.nickname = nickname;
        this.sex = sex;
        this.user_address = user_address;
        this.userId =userId;
        //this.user_photo =user_photo;
        this.user_phone = user_phone;
    }
    //修改
    public UserBean( String nickname, String sex, String user_address, Integer userId,String user_photo, String user_phone) {

        this.nickname = nickname;
        this.sex = sex;
        this.user_address = user_address;
        this.userId =userId;
        this.user_photo =user_photo;
        this.user_phone = user_phone;
    }
//查找
    public UserBean(String email, String password, String nickname, String sex, String user_address, Integer userId, String user_phone, String user_photo) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.sex = sex;
        this.user_address = user_address;
        this.userId = userId;
        this.user_phone = user_phone;
        this.user_photo = user_photo;
    }


    @Override
    public String toString() {
        return "UserBean{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", role='" + role + '\'' +
                ", sex='" + sex + '\'' +
                ", user_photo='" + user_photo + '\'' +
                ", user_phone='" + user_phone + '\'' +
                ", user_address='" + user_address + '\'' +
                '}';
    }


}

