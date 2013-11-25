package com.paperv.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.paperv.core.Constants;
import com.paperv.core.PapervActivity;
import com.paperv.core.PapervFragment;
import com.paperv.helpers.FeedHelper;
import com.paperv.www.MainActivity;
import com.paperv.www.R;

public class HomeTab extends PapervFragment{


	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		MainActivity.page_title.setText("Home");
		
		View v =  inflater.inflate(R.layout.fragment_home, null);
		GridView grid = (GridView) v.findViewById(R.id.feed_grid);
		grid.setAdapter(new FeedHelper(activityInstance));
		
		if ( ((PapervActivity)getActivity()).screenWidth < Constants.SMALL_SCREEN_SIZE)
			grid.setNumColumns(1);
		else
			grid.setNumColumns(2);

		return v;
	}
	
	
	

	@Override
	public void onClick(View arg0) {
		
	}

	@Override
	public void loadData() {
		
		
		apiHandler.home(activityInstance, activityInstance.appInstance.getUserName(), 1);
		
	}


	@Override
	public void loadMore(int pageNum) {
		
	}



	

}
