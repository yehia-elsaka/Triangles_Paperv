package paperv.tabs_utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import paperv.models.Friend;
import paperv.models.LikeStory;
import paperv.models.NotificationItem;
import paperv.models.Story;
import paperv.models.User;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.net.Uri;



public class GlobalState {
	
	
	private static GlobalState instance = new GlobalState();
	public static GlobalState getInstance(){
		
		return instance;
	}
	
	
	public boolean open_profile_tab = false;

	public User user = new User();
	public Uri glide_image;
	public Uri profile_image;
	
	public boolean is_glide;
	public boolean is_profile;
	
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
	
	
	public int user_followers = 0;
	public int user_following = 0;
	
	
	
	
	
	

}
