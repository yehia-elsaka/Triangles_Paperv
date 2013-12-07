package com.paperv.api;

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
	public boolean custom_doInBackground(JSONObject obj) {
		try {
			boolean success = obj.getBoolean("success");
			toastValue = obj.optString("msg");
			return success;
		} catch (Exception e) {
			toastValue = "Error sending request";
			return false;
		}	}

	@Override
	public void custom_onPostExecute(boolean result) {
		activityInstance.showLongToast(toastValue);
		if(result)
		{
			cache.story_view.likes_number++;
			TextView numLikes = (TextView)activityInstance.findViewById(R.id.likes_number_2);
			numLikes.setText(cache.story_view.likes_number+"");
		}
	}

}
