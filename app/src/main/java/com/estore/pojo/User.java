package com.estore.pojo;

import java.io.Serializable;

public class User implements Serializable {
	@Override
	public String toString() {
		return "User{" +
				"userId=" + userId +
				", userName='" + userName + '\'' +
				", userPwd='" + userPwd + '\'' +
				", phone='" + phone + '\'' +
				", nick='" + nick + '\'' +
				", imageUrl='" + imageUrl + '\'' +
				", userSex='" + userSex + '\'' +
				", storeName='" + storeName + '\'' +
				'}';
	}

	private Integer userId;
	private String userName;
	private String userPwd;
	private String phone;

	public String getEamil() {
		return eamil;
	}

	public void setEamil(String eamil) {
		this.eamil = eamil;
	}

	private String nick;
	private String imageUrl;
	private String eamil;
	public User(){}

	public User(String userName, String userPwd, String nick) {
		this.userName = userName;
		this.userPwd = userPwd;
		this.nick = nick;
	}

	public User(Integer userId, String userName, String userPwd, String phone, String nick, String imageUrl,
				String userSex, String storeName) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userPwd = userPwd;
		this.phone = phone;
		this.nick = nick;
		this.imageUrl = imageUrl;
		this.userSex = userSex;
		this.storeName = storeName;
	}
	private String userSex;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getUserSex() {
		return userSex;
	}
	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	private String storeName;//�̵���
	

}
