package com.example.db;

public class DAO {

	private String atyId;
	private String atyName;
	private String atyContent;
	private String created_time;
	private String deadline;
	
	public DAO(){
		
	}
	
	public DAO(String atyId, String atyName, String atyContent,
			String created_time, String deadline) {
		super();
		this.atyId = atyId;
		this.atyName = atyName;
		this.atyContent = atyContent;
		this.created_time = created_time;
		this.deadline = deadline;
	}
	public String getAtyId() {
		return atyId;
	}
	public void setAtyId(String atyId) {
		this.atyId = atyId;
	}
	public String getAtyName() {
		return atyName;
	}
	public void setAtyName(String atyName) {
		this.atyName = atyName;
	}
	public String getAtyContent() {
		return atyContent;
	}
	public void setAtyContent(String atyContent) {
		this.atyContent = atyContent;
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
	}
	
	 
}
