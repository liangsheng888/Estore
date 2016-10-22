package com.estore.pojo;

import java.io.Serializable;

public class User implements Serializable {
	private Integer userId;
	private String userName;
	private String email;
	private String nick;
	private String imageUrl;
	private String userSex;
	private String userPwd;
	private String phone;
	private String address;//住址
	private String storeName;//�̵���
	public User(){}
	//FindUser
	public User(String email, String password, String nickname, String sex, String user_address, Integer userId, String user_phone, String user_photo) {
		this.email = email;
		this.userPwd = password;
		this.nick = nickname;
		this.userSex = sex;
		this.address = user_address;
		this.userId = userId;
		this.phone = user_phone;
		this.imageUrl = user_photo;
	}
	//ModifyUser
	public User( String nickname, String sex, String user_address, Integer userId, String user_phone) {

		this.nick = nickname;
		this.userSex = sex;
		this.address = user_address;
		this.userId =userId;
		this.phone = user_phone;
	}







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

	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEamil() {
		return email;
	}

	public void setEamil(String eamil) {
		this.email = eamil;
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

/*

 */




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
}
