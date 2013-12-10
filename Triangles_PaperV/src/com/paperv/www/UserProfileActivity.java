package com.paperv.www;

import java.util.ArrayList;

import android.os.Bundle;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

import com.paperv.core.Constants;
import com.paperv.core.PapervActivity;
import com.paperv.helpers.FeedHelper;
import com.paperv.lazy_adapter_utils.LazyImageLoader;
import com.paperv.models.Story;

public class UserProfileActivity extends PapervActivity{
	FeedHelper adapter;
	String userID;
	TextView page_title;
	@Override
	public void onCreateUI(Bundle savedInstanceState) {
		setContentView(R.layout.activity_profile_feed);
		userID = appInstance.getUserID();
		page_title = (TextView) findViewById(R.id.page_title);
		page_title.setText("Profile");

		if(cache.selected_user_id != null && !cache.selected_user_id.equalsIgnoreCase(""))
			userID = cache.selected_user_id;
		
		
		final GridView grid = (GridView) findViewById(R.id.feed_grid);
		adapter = new FeedHelper(this, cache.user_feed_list);
		grid.setAdapter(adapter);
		if (screenWidth < Constants.SMALL_SCREEN_SIZE)
			grid.setNumColumns(1);
		else
			grid.setNumColumns(2);
		
		grid.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView arg0, int arg1) {
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemsCount, int totalItemsCount) {
				boolean condition1 = ((firstVisibleItem + visibleItemsCount) == totalItemsCount);
				boolean condition2 = (totalItemsCount % 25 == 0);
				boolean condition3 = (totalItemsCount > 0);
				

				
				if(condition1 && condition2 && condition3)
				{
					Log.d("helal", "condition true");
					int pageNum = (totalItemsCount / 25);
					loadMore(pageNum);
					adapter.notifyDataSetChanged();
				}
				else
					Log.d("helal", "condition false");

			}
		});
		
		LazyImageLoader imgLoader = new LazyImageLoader(this);
		TextView profileImage = (TextView)findViewById(R.id.profile_user_image);
		imgLoader.DisplayImage(cache.user.imageURL, this, profileImage);
		
		TextView numStories = (TextView)findViewById(R.id.number_of_stories);
		TextView numFollowers = (TextView)findViewById(R.id.number_of_followers);
		TextView numFollowing = (TextView)findViewById(R.id.number_of_following);
		
		loadData();
		
	}
	
	
	
	public void loadData() {
		cache.user_feed_list = new ArrayList<Story>();
		adapter.clear();
		apiHandler.userFeed(this, userID, 0);
		apiHandler.getProfile(this, userID);
	}

	public void loadMore(int pageNum) {
		apiHandler.userFeed(this, userID, pageNum);

	}
	

}
