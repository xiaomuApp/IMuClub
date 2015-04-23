package com.example.model;

import cn.bmob.v3.BmobUser;

public class UserInfor extends BmobUser {

	private String Id;
	private String club;
	private String InstallationId;//用户最近登录的手机特有id
	
	public void setId(String id) {
		Id = id;
	}
	public String getId() {
		return Id;
	}
	
	public void setClub(String club) {
		this.club = club;
	}
	public String getClub() {
		return club;
	}
	
	public void setInstallationId(String installationId) {
		InstallationId = installationId;
	}
	public String getInstallationId() {
		return InstallationId;
	}
}
