package com.paperv.models;

public class Comment {
	
	
	public String comment_id;
	public String comment_text;
	public String user_id;
	public String user_name;
	public String user_full_name;
	public String user_image_url;
	
	
	
	public Comment(){
		
	}



	public Comment(String comment_id, String text, String user_id,
			String user_name, String user_full_name, String user_image_url) {
		super();
		this.comment_id = comment_id;
		this.comment_text = text;
		this.user_id = user_id;
		this.user_name = user_name;
		this.user_full_name = user_full_name;
		this.user_image_url = user_image_url;
	}



	public String getComment_id() {
		return comment_id;
	}



	public void setComment_id(String comment_id) {
		this.comment_id = comment_id;
	}



	public String getCommentText() {
		return comment_text;
	}



	public void setCommentText(String text) {
		this.comment_text = text;
	}



	public String getUser_id() {
		return user_id;
	}



	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}



	public String getUser_name() {
		return user_name;
	}



	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}



	public String getUser_full_name() {
		return user_full_name;
	}



	public void setUser_full_name(String user_full_name) {
		this.user_full_name = user_full_name;
	}



	public String getUser_image_url() {
		return user_image_url;
	}



	public void setUser_image_url(String user_image_url) {
		this.user_image_url = user_image_url;
	}
	
	
	
	

}
