package com.example.entity;


public class User {
	private String username;
	private String time;
	
	public User(String username, String time) {
		super();
		this.username = username;
		this.time = time;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getTime() {
		return time;
	}
	
}
