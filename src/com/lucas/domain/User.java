package com.lucas.domain;

public class User {
	private int id;
	private String name;
	private String password;
	private String avator;
	private String age;
	private String gender;
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(String name, String password, String avator, String age, String gender) {
		super();
		this.name = name;
		this.password = password;
		this.avator = avator;
		this.age = age;
		this.gender = gender;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAvator() {
		return avator;
	}
	public void setAvator(String avator) {
		this.avator = avator;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", password=" + password + ", avator=" + avator + ", age=" + age
				+ ", gender=" + gender + "]";
	}
	
	
}
