package com.paperv.api;

import org.json.JSONObject;

import com.paperv.core.PapervActivity;
import com.paperv.models.User;

public class LoginAPI extends APIConnector{

	
	public LoginAPI(PapervActivity activityInstance, boolean showDialog,
			String url, String dialogText) {
		super(activityInstance, showDialog, url, dialogText);
	}

	@Override
	public boolean custom_doInBackground(JSONObject json) {
		
		boolean success = json.optBoolean("success");
		String user_id = json.optString("user_id");
		String user_name = json.optString("user_name");
		String full_name = json.optString("full_name");
		String email = json.optString("email");
		String user_image = json.optString("user_image");
		
		activityInstance.cache.user = new User(user_id, user_name, email, downloadBitmap(user_image));
		return success;
	}

	@Override
	public void custom_onPostExecute(boolean result) {
		
		if(result)
			activityInstance.showLongToast("Successfuly logged in");
		else
			activityInstance.showLongToast("Login failed");
		
	}

}
