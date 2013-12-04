package com.paperv.api;

import org.json.JSONObject;

import com.paperv.core.PapervActivity;

public class RegisterAPI extends APIConnector{

	
	
	public RegisterAPI(PapervActivity activityInstance, boolean showDialog,
			String url, String dialogText) {
		super(activityInstance, showDialog, url, dialogText);
	}

	@Override
	public boolean custom_doInBackground(JSONObject json) {
		
		boolean success = json.optBoolean("success");
		String user_id = json.optString("user_id");
		return success;
	}

	@Override
	public void custom_onPostExecute(boolean result) {
		if (result)
			activityInstance.showLongToast("User registered successfully!");
		else
			activityInstance.showLongToast("Register failed .. username or email already exists");
	}

}
