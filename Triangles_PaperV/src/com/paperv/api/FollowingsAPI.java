package com.paperv.api;

import org.json.JSONArray;
import org.json.JSONObject;

import com.paperv.core.PapervActivity;
import com.paperv.models.User;

public class FollowingsAPI extends APIConnector{

	public FollowingsAPI(PapervActivity activityInstance, boolean showDialog,
			String url, String dialogText) {
		super(activityInstance, showDialog, url, dialogText);
	}

	@Override
	public boolean custom_doInBackground(JSONObject json) {
		boolean success = json.optBoolean("success");
		JSONArray data = json.optJSONArray("data");
		for(int i = 0 ; i < data.length() ; i ++)
		{
			JSONObject obj = (JSONObject)data.optJSONObject(i);
			String id = obj.optString("user_id");
			String fullName = obj.optString("user_fullname");
			String imageURL = obj.optString("user_image");
			User user = new User(id, "", "", fullName, imageURL);
			cache.user.following.add(user);
		}
		return success;
	}

	@Override
	public void custom_onPostExecute(boolean result) {
		
	}
	

}
