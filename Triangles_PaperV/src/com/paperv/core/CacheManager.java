package com.paperv.core;

import java.util.ArrayList;

import android.content.SharedPreferences;
import android.net.Uri;

import com.paperv.models.Friend;
import com.paperv.models.LikeStory;
import com.paperv.models.NotificationItem;
import com.paperv.models.Story;
import com.paperv.models.User;



public class CacheManager {
	
	
	private static CacheManager instance = new CacheManager();
	public static CacheManager getInstance(){
		
		return instance;
	}
	
	
	public boolean open_profile_tab = false;

	public User user = new User();
	public Uri glide_image;
	public Uri profile_image;
	
	public boolean is_glide = false;
	public boolean is_profile = false;
	public boolean is_logout = false;
	
	public boolean in_show_mode = false;
	
	
	public SharedPreferences prefs;
	
	public ArrayList<Uri> images = new ArrayList<Uri>();
	
	
	
	 
	// Explore Stories List
	public ArrayList<Story> explore_list = new ArrayList<Story>();
	
	public ArrayList<Story> feed_list = new ArrayList<Story>();
	
	public ArrayList<Story> userStories_list = new ArrayList<Story>();
	
	
	public ArrayList<NotificationItem> notification_list = new ArrayList<NotificationItem>();
	
	public ArrayList<LikeStory> like_list = new ArrayList<LikeStory>();
	
	
	public ArrayList<Friend> friends_list = new ArrayList<Friend>();
	
	public Story story_view = new Story();
	
	
	public ArrayList<Friend> followers_list = new ArrayList<Friend>();
	
	public ArrayList<Friend> following_list = new ArrayList<Friend>();
	
	
	
	
	public int screenWidth;
	public int screenHeight;

}
