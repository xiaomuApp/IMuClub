package com.example.model;

import cn.bmob.v3.BmobObject;

public class TaskModel extends BmobObject {

	private String theme;
	private String deadline;
	private String builder;
	private String task;
	private String StudentId;
	private boolean Isdeclare;
	private boolean Iscomplete;
	
	
	
	//特殊功能
	private boolean isShow = false;
	
	

	public boolean isShow() {
		return isShow;
	}

	public void setShow(boolean isShow) {
		this.isShow = isShow;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public boolean isIsdeclare() {
		return Isdeclare;
	}

	public void setIsdeclare(boolean isdeclare) {
		Isdeclare = isdeclare;
	}

	public boolean isIscomplete() {
		return Iscomplete;
	}

	public void setIscomplete(boolean iscomplete) {
		Iscomplete = iscomplete;
	}

	public String getBuilder() {
		return builder;
	}

	public void setBuilder(String builder) {
		this.builder = builder;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}
	
	public void setStudentId(String studentId) {
		this.StudentId = studentId;
	}

	public String getStudentId() {
		return StudentId;
	}
}
