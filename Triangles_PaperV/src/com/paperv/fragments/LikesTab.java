package com.paperv.fragments;

import java.util.ArrayList;

import android.location.Address;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.GridView;

import com.paperv.core.Constants;
import com.paperv.core.PapervActivity;
import com.paperv.core.PapervFragment;
import com.paperv.helpers.FeedHelper;
import com.paperv.models.Story;
import com.paperv.www.MainActivity;
import com.paperv.www.R;

public class LikesTab extends PapervFragment{

	FeedHelper adapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		MainActivity.page_title.setText("Likes");
		
		View v =  inflater.inflate(R.layout.fragment_home, null);
		final GridView grid = (GridView) v.findViewById(R.id.feed_grid);
		
		adapter = new FeedHelper(activityInstance, cache.likes_list);
		grid.setAdapter(adapter);
		
		if (((PapervActivity)getActivity()).screenWidth < Constants.SMALL_SCREEN_SIZE)
			grid.setNumColumns(1);
		else
			grid.setNumColumns(2);
		
		grid.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemsCount, int totalItemsCount) {
				boolean condition1 = ((firstVisibleItem + visibleItemsCount) == totalItemsCount);
				boolean condition2 = (totalItemsCount % 25 == 0);
				boolean condition3 = (totalItemsCount > 0);
				

				
				if(condition1 && condition2 && condition3)
				{
					Log.d("helal", "condition true");
					int pageNum = (totalItemsCount / 25)+1;
					loadMore(pageNum);
					adapter.notifyDataSetChanged();
				}
				else
					Log.d("helal", "condition false");

			}
		});
		return v;
	}
	
	

	@Override
	public void onClick(View arg0) {
		
	}

	@Override
	public void loadData() {
		
		cache.likes_list = new ArrayList<Story>();
		adapter.clear();
		apiHandler.likes(activityInstance, activityInstance.appInstance.getUserID(), 1);
	}


	@Override
	public void loadMore(int pageNum) {
		apiHandler.likes(activityInstance, activityInstance.appInstance.getUserID(), pageNum);
	}



	

}
