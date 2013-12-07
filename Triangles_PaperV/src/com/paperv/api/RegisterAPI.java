package com.paperv.api;

import org.json.JSONObject;

import android.content.Intent;

import com.paperv.core.PapervActivity;
import com.paperv.www.LoginActivity;

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
		{
			activityInstance.showLongToast("User registered successfully!");
			Intent intent = new Intent(activityInstance, LoginActivity.class);
			activityInstance.finish();
			activityInstance.startActivity(intent);
		}
		else
			activityInstance.showLongToast("Register failed .. username or email already exists");
	}

}
