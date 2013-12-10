package com.paperv.api;

import org.json.JSONObject;

import android.widget.TextView;

import com.paperv.core.PapervActivity;
import com.paperv.lazy_adapter_utils.LazyImageLoader;
import com.paperv.models.User;
import com.paperv.www.MainActivity;
import com.paperv.www.R;

public class ProfileAPI extends APIConnector{
	
	
	User user;

	
	public ProfileAPI(PapervActivity activityInstance, boolean showDialog,
			String url, String dialogText) {
		super(activityInstance, showDialog, url, dialogText);
	}

	@Override
	public boolean custom_doInBackground(JSONObject json) {
		boolean success = json.optBoolean("success");
		JSONObject data = json.optJSONObject("data");
		
		String id = data.optString("user_id");
		String name = data.optString("user_name");
		String fullName = data.optString("user_fullname");
		String email = data.optString("user_email");
		String imageURL = data.optString("user_image");
		user = new  User(id, name, email, fullName, imageURL);
		
		return success;
	}

	@Override
	public void custom_onPostExecute(boolean result) {
		LazyImageLoader imgLaoder = new LazyImageLoader(activityInstance);
		if(result)
		{
			MainActivity.page_title.setText(user.fullName+"");
			
			TextView image = (TextView)activityInstance.findViewById(R.id.profile_user_image);
			imgLaoder.DisplayImage(user.imageURL, activityInstance, image);
		}
		else
		{
			
		}
	}
	

}
