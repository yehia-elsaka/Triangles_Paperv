package com.paperv.models;

import java.util.ArrayList;

import android.graphics.Bitmap;

public class User {
	
	public String id;
	public String name;
	public String email;
	public String fullName;
	public String imageURL;
	private Bitmap image;
	public ArrayList<User> following = new ArrayList<User>();
	
	
	public User()
	{
		
	}

	
	
	public User(String id, String name, String email, String fullName,
			String imageURL) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.fullName = fullName;
		this.imageURL = imageURL;
	}



	public User(String id, String name, String email, Bitmap image) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.image = image;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public Bitmap getImage() {
		return image;
	}


	public void setImage(Bitmap image) {
		this.image = image;
	}
	
	
	
	
	
	
	
	

}
