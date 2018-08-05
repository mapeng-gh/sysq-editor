package com.huasheng.sysq.editor.params;

public class UserCreateRequest {

	private String loginName;
	private String loginPwd;
	private String name;
	private String userType;
	private String email;
	private String workingPlace;
	private String mobile;
	
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getLoginPwd() {
		return loginPwd;
	}
	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getWorkingPlace() {
		return workingPlace;
	}
	public void setWorkingPlace(String workingPlace) {
		this.workingPlace = workingPlace;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
