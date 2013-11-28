package com.paperv.core;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.paperv.api.APIHandler;
import com.paperv.network.DataConnector;

public abstract class PapervActivity extends FragmentActivity{
	
	public PapervApp appInstance;
	public Context mContext = this;
	public CacheManager cache = CacheManager.getInstance();
	public DataConnector dataConnector = DataConnector.getInstance();
	public APIHandler apiHandler = APIHandler.getInstance();
	
	public int screenWidth;
	public int screenHeight;
	

	@Override
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//BugSenseHandler.initAndStartSession(this, "0da77729");
		appInstance = (PapervApp)getApplication();
		screenWidth = getWindowManager().getDefaultDisplay().getWidth();
		screenHeight = getWindowManager().getDefaultDisplay().getHeight();
		onCreateUI(savedInstanceState);
	};
	
	public abstract void onCreateUI(android.os.Bundle savedInstanceState);
	
	public void showLongToast(String text){
		Toast.makeText(mContext, text, Toast.LENGTH_LONG).show();
	}
	
	public Point getFeedDimensions(){
		Point p = new Point();
		if (screenWidth > Constants.SMALL_SCREEN_SIZE)
		{
			int w = (int) (cache.screenWidth / 2.1);
			int h = (int)(w * 1.2);
			p = new Point(w, h);
		}
		else
		{
			int w = (int) (cache.screenWidth / 1.1);
			int h = (int)(w * 1.2);
			p = new Point(w, h);
		}
		
		return p;
	}

}
