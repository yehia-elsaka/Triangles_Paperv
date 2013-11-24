package com.paperv.api;

import com.paperv.core.Constants;
import com.paperv.core.PapervActivity;

public class APIHandler {
	
	private static APIHandler instance = new APIHandler();
	public static APIHandler getInstance(){
		return instance;
	}
	
	
	// 1 - login
	LoginAPI loginAPI;
	public void login (PapervActivity activityInstance, boolean showDialog, String username, String password, String dialogText, boolean rememberMe){
		
		String url = Constants.API_LOGIN+"login="+username+"&password="+password;
		loginAPI = new LoginAPI(activityInstance, showDialog, url, dialogText);
		loginAPI.rememberMe = rememberMe;
		loginAPI.execute();
	}
	
	// 2 - Register
	RegisterAPI registerAPI;
	public void register (PapervActivity activityInstance, String username, String password, String fullname, String email, String dialogText)
	{
		String url = Constants.API_REGISTER+"user_name="+username
				+"&password="+password
				+"&email="+email
				+"&full_name="+fullname.replaceAll(" ", "%20");
		registerAPI = new RegisterAPI(activityInstance, true, url, dialogText);
		registerAPI.execute();
	}
	
	// 3 - Explore Feed
	ExploreAPI exploreAPI;
	public void explore (PapervActivity activityInstance, String user_id, int page)
	{
		String url = Constants.API_EXPLORE+"user_id="+user_id+"&page="+page;
		exploreAPI = new ExploreAPI(activityInstance, true, url, "Loading Explore Feed");
		exploreAPI.execute();
	}
	
	// 4 - Home Feed
	HomeAPI homeAPI;
	public void home(PapervActivity activityInstance, String user_id, int page)
	{
		String url = Constants.API_EXPLORE+"user_id="+user_id+"&page="+page;
		homeAPI = new HomeAPI(activityInstance, true, url, "Loading Home Feed");
		homeAPI.execute();
	}

}
