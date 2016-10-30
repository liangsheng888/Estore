package bean;

public class User {

	@Override
	public String toString() {
		return "User [active=" + active + ", activecode=" + activecode
				+ ", checkcode=" + checkcode + ", email=" + email + ", id="
				+ userId + ", nickname=" + nickname + ", password=" + password
				+ ", role=" + role + "]";
	}
	private int userId;//userid
	private String email;
	private String password;
	private String nickname;
	private String role;
	private int active;
	private String activecode;
	private String checkcode;
	private String userPhoto;
	private String user_address;
	private String user_phone;
	private String userSex;
	

	 	 //findUser
	public User(String email, String password, String nickname,
			String userSex, String userPhoto, String user_phone, String user_address) {
		super();
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.userSex = userSex;
		this.userPhoto = userPhoto;
		this.user_phone = user_phone;
		this.user_address = user_address;
	}


	public User(String email, String password, String nickname, String sex, String user_address, Integer userId, String user_phone, String user_photo) {
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.userSex = sex;
		this.user_address = user_address;
		this.userId = userId;
		this.user_phone = user_phone;
		this.userPhoto = user_photo;
	}
	//ModifyUser
	public User( String nickname, String sex, String user_address, Integer userId, String user_phone) {

		this.nickname = nickname;
		this.userSex = sex;
		this.user_address = user_address;
		this.userId =userId;
		this.user_phone = user_phone;
	}


	
	public User() {
		// TODO Auto-generated constructor stub
	}
	public String getUser_address() {
		return user_address;
	}

	public void setUser_address(String userAddress) {
		user_address = userAddress;
	}

	public String getUser_phone() {
		return user_phone;
	}

	public void setUser_phone(String userPhone) {
		user_phone = userPhone;
	}

	public String getUserSex() {
		return userSex;
	}

	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}

	public String getCheckcode() {
		return checkcode;
	}
	
	public String getUserPhoto() {
		return userPhoto;
	}

	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
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
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
	public String getActivecode() {
		return activecode;
	}
	public void setActivecode(String activecode) {
		this.activecode = activecode;
	}
	public void setUserPhoto(String userPhoto) {
		this.userPhoto=userPhoto;
		
	}
	
}
