package com.paperv.api;

import org.json.JSONObject;

import android.widget.TextView;

import com.paperv.core.PapervActivity;
import com.paperv.www.R;

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
			cache.story_view.comments_number++;
			TextView numComments = (TextView)activityInstance.findViewById(R.id.comments_number_2);
			numComments.setText(cache.story_view.comments_number+"");
		}
	}
	

}
