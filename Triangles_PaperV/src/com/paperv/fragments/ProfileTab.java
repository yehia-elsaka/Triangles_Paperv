package com.paperv.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.paperv.core.PapervFragment;
import com.paperv.www.MainActivity;
import com.paperv.www.R;

public class ProfileTab extends PapervFragment {

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		MainActivity.page_title.setText("Profile");
		activityInstance.setContentView(R.layout.activity_profile);
		return null;
	}
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadData() {
		
	}

	@Override
	public void loadMore(int pageNum) {
		
	}
	

}
