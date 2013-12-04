package com.paperv.api;

import org.json.JSONObject;

import android.widget.TextView;

import com.paperv.core.PapervActivity;
import com.paperv.www.R;

public class ReglideAPI extends APIConnector {

	public ReglideAPI(PapervActivity activityInstance, boolean showDialog,
			String url, String dialogText) {
		super(activityInstance, showDialog, url, dialogText);
	}

	@Override
	public boolean custom_doInBackground(JSONObject obj) {
		try {
			boolean success = obj.getBoolean("success");
			return success;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public void custom_onPostExecute(boolean result) {
		if(result)
		{
			activityInstance.showLongToast("Story Reglided");
			cache.story_view.reglide_number++;
			TextView numReglides = (TextView)activityInstance.findViewById(R.id.reglides_number_2);
			numReglides.setText(cache.story_view.reglide_number+"");
		}
		else
			activityInstance.showLongToast("Request failed");
	}

}
