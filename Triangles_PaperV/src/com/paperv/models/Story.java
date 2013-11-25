package com.paperv.models;

import java.util.ArrayList;

public class Story {
	
	public int story_id;
	public String owner_id;
	public String photo_url;
	public String story_name;
	public String user_name;
	public String user_image;
	public int likes_number;
	public int reglide_number;
	public int comments_number;
	
	public ArrayList<PhotoItem> photos = new ArrayList<PhotoItem>();
	public ArrayList<Comment> comments = new ArrayList<Comment>();
	
	
	
	public Story() {
	}



	public Story(int story_id, String owner_id, String photo_url,
			String story_name, String user_name, String user_image,
			int likes_number, int reglide_number, int comments_number,
			ArrayList<PhotoItem> photos, ArrayList<Comment> comments) {
		super();
		this.story_id = story_id;
		this.owner_id = owner_id;
		this.photo_url = photo_url;
		this.story_name = story_name;
		this.user_name = user_name;
		this.user_image = user_image;
		this.likes_number = likes_number;
		this.reglide_number = reglide_number;
		this.comments_number = comments_number;
		this.photos = photos;
		this.comments = comments;
	}



	public int getStory_id() {
		return story_id;
	}



	public void setStory_id(int story_id) {
		this.story_id = story_id;
	}



	public String getOwner_id() {
		return owner_id;
	}



	public void setOwner_id(String owner_id) {
		this.owner_id = owner_id;
	}



	public String getPhoto_url() {
		return photo_url;
	}



	public void setPhoto_url(String photo_url) {
		this.photo_url = photo_url;
	}



	public String getStory_name() {
		return story_name;
	}



	public void setStory_name(String story_name) {
		this.story_name = story_name;
	}



	public String getUser_name() {
		return user_name;
	}



	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}



	public String getUser_image() {
		return user_image;
	}



	public void setUser_image(String user_image) {
		this.user_image = user_image;
	}



	public int getLikes_number() {
		return likes_number;
	}



	public void setLikes_number(int likes_number) {
		this.likes_number = likes_number;
	}



	public int getReglide_number() {
		return reglide_number;
	}



	public void setReglide_number(int reglide_number) {
		this.reglide_number = reglide_number;
	}



	public int getComments_number() {
		return comments_number;
	}



	public void setComments_number(int comments_number) {
		this.comments_number = comments_number;
	}



	public ArrayList<PhotoItem> getPhotos() {
		return photos;
	}



	public void setPhotos(ArrayList<PhotoItem> photos) {
		this.photos = photos;
	}



	public ArrayList<Comment> getComments() {
		return comments;
	}



	public void setComments(ArrayList<Comment> comments) {
		this.comments = comments;
	}


	
	
	
	
	

}
