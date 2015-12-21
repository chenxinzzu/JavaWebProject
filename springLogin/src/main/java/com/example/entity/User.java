package com.example.entity;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Scanner;

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
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		time=df.format(new Date());
		return time;
	}
	
}
