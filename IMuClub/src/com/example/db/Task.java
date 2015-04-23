package com.example.db;

public class Task {

	private String taskId;
	private String taskName;
	private String taskContent;
	private String created_time;
	private String deadline;
	
	public Task(){
		
	}
	
	public Task(String taskId, String taskName, String taskContent,
			String created_time, String deadline) {
		super();
		this.taskId = taskId;
		this.taskName = taskName;
		this.taskContent = taskContent;
		this.created_time = created_time;
		this.deadline = deadline;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getTaskContent() {
		return taskContent;
	}
	public void setTaskContent(String taskContent) {
		this.taskContent = taskContent;
	}
	public String getCreated_time() {
		return created_time;
	}
	public void setCreated_time(String created_time) {
		this.created_time = created_time;
	}
	public String getDeadline() {
		return deadline;
	}
	public void setDeadline(String deadline) {
		this.deadline = deadline;
	};
	
	
}
