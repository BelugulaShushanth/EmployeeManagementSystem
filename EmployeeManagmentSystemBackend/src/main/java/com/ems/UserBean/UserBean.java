package com.ems.UserBean;

public class UserBean {
	
	private long userId;
	private String username;
	private String password;
	private String role;
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	@Override
	public String toString() {
		return "UserBean [userId=" + userId + ", username=" + username + ", password=" + password + ", role=" + role
				+ "]";
	}
	
	
	
}
