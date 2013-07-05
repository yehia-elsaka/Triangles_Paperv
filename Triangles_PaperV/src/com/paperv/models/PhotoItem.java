package com.paperv.models;

public class PhotoItem {
	
	String item_url;
	String caption;
	String type;
	
	
	
	
	public PhotoItem() {
		super();
		// TODO Auto-generated constructor stub
	}




	public PhotoItem(String item_url, String caption, String type) {
		super();
		this.item_url = item_url;
		this.caption = caption;
		this.type = type;
	}




	public String getItem_url() {
		return item_url;
	}




	public void setItem_url(String item_url) {
		this.item_url = item_url;
	}




	public String getCaption() {
		return caption;
	}




	public void setCaption(String caption) {
		this.caption = caption;
	}




	public String getType() {
		return type;
	}




	public void setType(String type) {
		this.type = type;
	}




	
	
	
	

}
