package com.paperv.api;

import org.json.JSONObject;

import com.paperv.core.PapervActivity;

public class FollowAPI extends APIConnector{

	public FollowAPI(PapervActivity activityInstance, boolean showDialog,
			String url, String dialogText) {
		super(activityInstance, showDialog, url, dialogText);
	}

	@Override
	public boolean custom_doInBackground(JSONObject json) {
		boolean success = json.optBoolean("success");
		return success;
	}

	@Override
	public void custom_onPostExecute(boolean result) {
		if (result)
		{
			activityInstance.showLongToast("User followed");
		}
		else
		{
			activityInstance.showLongToast("Follow request failed");
		}
	}
	

}
