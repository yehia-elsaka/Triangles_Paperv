package com.paperv.api;

import org.json.JSONObject;

import android.widget.Button;

import com.paperv.core.PapervActivity;
import com.paperv.www.R;

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
			Button followBtn = (Button)activityInstance.findViewById(R.id.follow_user);
			followBtn.setText("unfollow");
		}
		else
		{
			activityInstance.showLongToast("Follow request failed");
		}
	}
	

}
