package com.example.db;

public class User {
	
	private String uid;
	private String userName;
	private String userPassword;
	
	public User(){
		
	}
	
	public User(String uid, String userName, String userPassword) {
		super();
		this.uid = uid;
		this.userName = userName;
		this.userPassword = userPassword;
	}
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	
	

}
