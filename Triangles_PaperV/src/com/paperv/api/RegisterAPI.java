package com.paperv.api;

import org.json.JSONObject;

import com.paperv.core.PapervActivity;

public class RegisterAPI extends APIConnector{

	
	
	public RegisterAPI(PapervActivity activityInstance, boolean showDialog,
			String url, String dialogText) {
		super(activityInstance, showDialog, url, dialogText);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean custom_doInBackground(JSONObject json) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void custom_onPostExecute(boolean result) {
		// TODO Auto-generated method stub
		
	}

}
