package com.paperv.core;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class PapervApp extends Application {

	private static Context context;
	protected SharedPreferences prefs;

	@Override
	public void onCreate() {
		super.onCreate();
		context = getApplicationContext();
		prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}

	public static Context getAppContext() {
		return context;
	}
	
	//============ Shared Prefs Functions ======
	
	// 1 - Remember me
	public void setRememberMe(boolean rememberMe)
	{
		Editor editor = prefs.edit();
		editor.putBoolean(Constants.PREFS_REMEMBER_ME, rememberMe);
		editor.commit();
	}
	
	public boolean isRememberMe(){
		return prefs.getBoolean(Constants.PREFS_REMEMBER_ME, false);
	}
	
	// 2 - SAVE USERNAME & PASSWORD
	
	public void setUserName(String userName)
	{
		Editor editor = prefs.edit();
		editor.putString(Constants.PREFS_USERNAME, userName);
		editor.commit();
	}
	
	public String getUserName(){
		return prefs.getString(Constants.PREFS_USERNAME, "");
	}
	
	public void setPassword(String password)
	{
		Editor editor = prefs.edit();
		editor.putString(Constants.PREFS_PASSWORD, password);
		editor.commit();
	}
	
	public String getPassword(){
		return prefs.getString(Constants.PREFS_PASSWORD, "");
	}
	
	// 3 - 
	

}
