package com.paperv.api;

import org.json.JSONArray;
import org.json.JSONObject;

import android.widget.TextView;

import com.paperv.core.PapervActivity;
import com.paperv.www.R;

public class LikeAPI extends APIConnector{

	public LikeAPI(PapervActivity activityInstance, boolean showDialog,
			String url, String dialogText) {
		super(activityInstance, showDialog, url, dialogText);
	}

	@Override
	public boolean custom_doInBackground(JSONArray json) {
		try {
			JSONObject obj = json.getJSONObject(0);
			boolean success = obj.getBoolean("success");
			return success;
		} catch (Exception e) {
			return false;
		}	}

	@Override
	public void custom_onPostExecute(boolean result) {
		if(result)
		{
			activityInstance.showLongToast("Success");
			cache.story_view.likes_number++;
			TextView numLikes = (TextView)activityInstance.findViewById(R.id.likes_number_2);
			numLikes.setText(cache.story_view.likes_number+"");
		}
		else
			activityInstance.showLongToast("Request failed");	
	}

}
