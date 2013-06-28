package paperv.tabs_utils;

import java.util.ArrayList;

import paperv.models.NotificationItem;
import paperv.models.Story;
import paperv.models.User;
import android.net.Uri;



public class GlobalState {
	
	
	private static GlobalState instance = new GlobalState();
	public static GlobalState getInstance(){
		
		return instance;
	}
	
	

	public User user = new User();
	public Uri image;
	public ArrayList<Uri> images = new ArrayList<Uri>();
	
	
	
	
	// Explore Stories List
	public ArrayList<Story> explore_list = new ArrayList<Story>();
	
	public ArrayList<Story> feed_list = new ArrayList<Story>();
	
	public ArrayList<Story> userStories_list = new ArrayList<Story>();
	
	
	public ArrayList<NotificationItem> notification_list = new ArrayList<NotificationItem>();
	
	
	public Story story_view = new Story();
	
	
	public int user_followers = 0;
	public int user_following = 0;
	
	
	
	
	
	

}
