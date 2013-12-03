package com.paperv.api;

import org.json.JSONArray;

import com.paperv.core.PapervActivity;

public class GlideAPI extends APIConnector{
	
	

	public GlideAPI(PapervActivity activityInstance, boolean showDialog,
			String url, String dialogText) {
		super(activityInstance, showDialog, url, dialogText);
	}

	@Override
	public boolean custom_doInBackground(JSONArray json) {
		
		
		return false;
	}

	@Override
	public void custom_onPostExecute(boolean result) {
		if(result)
			activityInstance.showLongToast("Story glided successfuly");
		else
			activityInstance.showLongToast("Story glide failed");
				
	}
	

}
