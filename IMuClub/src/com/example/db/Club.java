package com.example.db;

public class Club {
	private String clubId;
	private String clubName;
	
	public Club(){
		
	}
	
	public Club(String clubId, String clubName) {
		super();
		this.clubId = clubId;
		this.clubName = clubName;
	}
	public String getClubId() {
		return clubId;
	}
	public void setClubId(String clubId) {
		this.clubId = clubId;
	}
	public String getClubName() {
		return clubName;
	}
	public void setClubName(String clubName) {
		this.clubName = clubName;
	}

	
}
