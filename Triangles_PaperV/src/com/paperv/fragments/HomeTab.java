package com.paperv.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.paperv.core.PapervFragment;
import com.paperv.www.MainActivity;
import com.paperv.www.R;

public class HomeTab extends PapervFragment{


	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		MainActivity.page_title.setText("Home");
		return inflater.inflate(R.layout.fragment_home, null);
		
	}
	
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadData() {
		apiHandler.home(activityInstance, activityInstance.appInstance.getUserName(), 1);
	}


	@Override
	public void loadMore(int pageNum) {
		
	}

}
