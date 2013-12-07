package com.paperv.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.TextView;

import com.paperv.core.Constants;
import com.paperv.core.PapervActivity;
import com.paperv.core.PapervFragment;
import com.paperv.helpers.FeedHelper;
import com.paperv.lazy_adapter_utils.LazyImageLoader;
import com.paperv.models.Story;
import com.paperv.www.MainActivity;
import com.paperv.www.R;

public class ProfileTab extends PapervFragment {
	FeedHelper adapter;

	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		MainActivity.page_title.setText(cache.user.fullName+"");
		
		View v =  inflater.inflate(R.layout.fragment_profile_feed, null);
		final GridView grid = (GridView) v.findViewById(R.id.feed_grid);
		adapter = new FeedHelper(activityInstance, cache.user_feed_list);
		grid.setAdapter(adapter);
		if (((PapervActivity)getActivity()).screenWidth < Constants.SMALL_SCREEN_SIZE)
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
		
		LazyImageLoader imgLoader = new LazyImageLoader(activityInstance);
		TextView profileImage = (TextView)v.findViewById(R.id.profile_user_image);
		imgLoader.DisplayImage(cache.user.imageURL, activityInstance, profileImage);
		
		TextView numStories = (TextView)v.findViewById(R.id.number_of_stories);
		TextView numFollowers = (TextView)v.findViewById(R.id.number_of_followers);
		TextView numFollowing = (TextView)v.findViewById(R.id.number_of_following);
		
		
		return v;
	}
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadData() {
		cache.user_feed_list = new ArrayList<Story>();
		adapter.clear();
		apiHandler.userFeed(activityInstance, activityInstance.appInstance.getUserID(), 0);

	}

	@Override
	public void loadMore(int pageNum) {
		apiHandler.userFeed(activityInstance, activityInstance.appInstance.getUserID(), pageNum);

	}
	

}
