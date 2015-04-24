package com.example.model;


public class PeopleModel {

	private String name;
	private String nickname;
	private String position;
	private boolean isVisible;
	
	public PeopleModel() {
		isVisible = false;
	}
	
	public PeopleModel(String name, String nickname, String position,
			boolean isVisible) {
		super();
		this.name = name;
		this.nickname = nickname;
		this.position = position;
		this.isVisible = isVisible;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getNickname() {
		return nickname;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public String getPosition() {
		return position;
	}
	
	public void setPosition(String position) {
		this.position = position;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
	
}
