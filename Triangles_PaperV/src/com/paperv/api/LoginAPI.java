package com.paperv.api;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.util.Log;

import com.paperv.core.PapervActivity;
import com.paperv.models.User;
import com.paperv.www.MainActivity;
import com.paperv.www.R;

public class LoginAPI extends APIConnector{

	public boolean rememberMe = false;
	public LoginAPI(PapervActivity activityInstance, boolean showDialog,
			String url, String dialogText) {
		super(activityInstance, showDialog, url, dialogText);

		
		
	}

	@Override
	public boolean custom_doInBackground(JSONObject json) {
		boolean success = json.optBoolean("success");
		String user_id = json.optString("user_id");
		if(success)
		{
			activityInstance.appInstance.setUserId(user_id);
		}
		
		String user_name = json.optString("user_name");
		String full_name = json.optString("full_name");
		String email = json.optString("email");
		String user_image = json.optString("user_image");
		
		activityInstance.cache.user = new User(user_id, user_name, email, full_name, user_image);
		return success;
	}

	@Override
	public void custom_onPostExecute(boolean result) {
		
		if(result){
			activityInstance.appInstance.setRememberMe(rememberMe);
			activityInstance.finish();
			Intent i = new Intent(activityInstance, MainActivity.class);
			activityInstance.startActivityForResult(i, 700);
			activityInstance.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
		}
		else
		{
			activityInstance.appInstance.setRememberMe(false);
			activityInstance.showLongToast("Login failed");
		}
		
	}

}
