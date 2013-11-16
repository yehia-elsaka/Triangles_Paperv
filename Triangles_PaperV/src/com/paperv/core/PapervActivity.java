package com.paperv.core;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.bugsense.trace.BugSenseHandler;
import com.paperv.api.APIHandler;
import com.paperv.network.DataConnector;

public abstract class PapervActivity extends FragmentActivity{
	
	public PapervApp appInstance;
	public Context mContext = this;
	public CacheManager cache = CacheManager.getInstance();
	public DataConnector dataConnector = DataConnector.getInstance();
	public APIHandler apiHandler = APIHandler.getInstance();


	@Override
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		BugSenseHandler.initAndStartSession(this, "0da77729");
		appInstance = (PapervApp)getApplication();
		onCreateUI(savedInstanceState);
	};
	
	public abstract void onCreateUI(android.os.Bundle savedInstanceState);
	
	public void showLongToast(String text){
		Toast.makeText(mContext, text, Toast.LENGTH_LONG).show();
	}

}
