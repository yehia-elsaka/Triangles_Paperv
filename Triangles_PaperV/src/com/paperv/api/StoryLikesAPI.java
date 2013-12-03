package com.paperv.api;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;
import android.widget.GridView;

import com.paperv.core.CacheManager;
import com.paperv.core.PapervActivity;
import com.paperv.helpers.FeedHelper;
import com.paperv.models.Story;
import com.paperv.www.R;

public class StoryLikesAPI extends APIConnector {

	public ArrayList<Story> stories = new ArrayList<Story>();

	public StoryLikesAPI(PapervActivity activityInstance, boolean showDialog,
			String url, String dialogText) {
		super(activityInstance, showDialog, url, dialogText);
	}

	@Override
	public boolean custom_doInBackground(JSONArray json) {
		try {
			JSONObject obj = json.getJSONObject(0);
			boolean success = obj.getBoolean("success");
			JSONArray data = obj.getJSONArray("data");
			for (int i = 0; i < data.length(); i++) {
				try {
					JSONObject j = data.getJSONObject(i);
					int story_id = j.getInt("story_id");
					String owner_id = j.getString("user_id");
					String photo_url = j.getString("photourl");
					String story_name = j.getString("title");
					String user_name = j.getString("user_fullname");
					String user_image = j.getString("user_image");
					int likes_number = j.getInt("total_like");
					int reglide_number = j.getInt("total_repost");
					int comments_number = j.getInt("total_comment");
					Story story = new Story(story_id, owner_id, photo_url,
							story_name, user_name, user_image, likes_number,
							reglide_number, comments_number, null, null);

					stories.add(story);
					CacheManager.getInstance().likes_list.add(story);

				} catch (Exception e) {

				}
			}
			return success;
		} catch (Exception e) {
			return false;

		}
	}

	@Override
	public void custom_onPostExecute(boolean result) {
		GridView grid = (GridView) activityInstance
				.findViewById(R.id.feed_grid);
		FeedHelper helper = (FeedHelper) grid.getAdapter();

		for (int i = 0; i < stories.size(); i++) {
			CacheManager.getInstance().likes_list.add(new Story());
		}
		helper.releod(cache.likes_list);
	}

}
