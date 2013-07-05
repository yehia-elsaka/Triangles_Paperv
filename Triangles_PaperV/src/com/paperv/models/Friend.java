package com.paperv.models;

public class Friend {
	
	private int friend_id;
	private String friend_name;
	private String full_name;
	private String friend_image;
	
	
	public Friend()
	{
		
	}


	public Friend(int friend_id, String friend_name, String full_name,
			String friend_image) {
		super();
		this.friend_id = friend_id;
		this.friend_name = friend_name;
		this.full_name = full_name;
		this.friend_image = friend_image;
	}


	public int getFriend_id() {
		return friend_id;
	}


	public void setFriend_id(int friend_id) {
		this.friend_id = friend_id;
	}


	public String getFriend_name() {
		return friend_name;
	}


	public void setFriend_name(String friend_name) {
		this.friend_name = friend_name;
	}


	public String getFull_name() {
		return full_name;
	}


	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}


	public String getFriend_image() {
		return friend_image;
	}


	public void setFriend_image(String friend_image) {
		this.friend_image = friend_image;
	}
	
	
	
	
	
	
	

}
