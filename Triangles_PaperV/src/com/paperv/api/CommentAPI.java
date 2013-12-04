package com.paperv.api;

import org.json.JSONObject;

import com.paperv.core.PapervActivity;

public class CommentAPI extends APIConnector{

	public CommentAPI(PapervActivity activityInstance, boolean showDialog,
			String url, String dialogText) {
		super(activityInstance, showDialog, url, dialogText);
	}

	@Override
	public boolean custom_doInBackground(JSONObject json) {
		return json.optBoolean("success");
	}

	@Override
	public void custom_onPostExecute(boolean result) {
		if(result)
		{
			activityInstance.showLongToast("Comment success");
		}
		else
		{
			activityInstance.showLongToast("Comment failed");
		}
	}
	

}
