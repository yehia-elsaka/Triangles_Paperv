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
	public void login (PapervActivity activityInstance, boolean showDialog, String username, String password, String dialogText){
		
		String url = Constants.API_LOGIN+"login="+username+"&password="+password;
		loginAPI = new LoginAPI(activityInstance, showDialog, url, dialogText);
		loginAPI.execute();
	}
	
	

}
