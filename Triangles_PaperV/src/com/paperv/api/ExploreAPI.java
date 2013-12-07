package com.paperv.api;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.widget.GridView;

import com.paperv.core.CacheManager;
import com.paperv.core.PapervActivity;
import com.paperv.helpers.FeedHelper;
import com.paperv.models.Story;
import com.paperv.www.R;

public class ExploreAPI extends APIConnector {
	public ArrayList<Story> stories = new ArrayList<Story>();

	public ExploreAPI(PapervActivity activityInstance, boolean showDialog,
			String url, String dialogText) {
		super(activityInstance, showDialog, url, dialogText);
	}

	@Override
	public boolean custom_doInBackground(JSONObject json) {
		
		boolean success = json.optBoolean("success");

		JSONArray data = json.optJSONArray("data");
		if (data != null)
			for (int i = 0; i < data.length(); i++) {
				try {
					JSONObject jsonObj = data.getJSONObject(i);

					int story_id = jsonObj.getInt("story_id");
					String story_name = jsonObj.getString("title");
					int comments_number = Integer.parseInt(jsonObj
							.getString("total_comment"));
					int likes_number = Integer.parseInt(jsonObj
							.getString("total_like"));
					int reglide_number = Integer.parseInt(jsonObj
							.getString("total_repost"));
					String photo_url = jsonObj.getString("photourl");
					String owner_id = jsonObj.getString("user_id");
					String user_image = jsonObj.getString("user_image");
					String user_name = jsonObj.getString("user_fullname");

					Story story = new Story(story_id, owner_id, photo_url,
							story_name, user_name, user_image, likes_number,
							reglide_number, comments_number, null, null);
					stories.add(story);

					activityInstance.cache.explore_list.add(story);

				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
		return success;
	}

	@Override
	public void custom_onPostExecute(boolean result) {
		GridView grid = (GridView) activityInstance
				.findViewById(R.id.feed_grid);
		FeedHelper helper = (FeedHelper) grid.getAdapter();

		helper.releod(cache.explore_list);
	}

}
