package com.paperv.www;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.widget.LinearLayout;

import com.bugsense.trace.BugSenseHandler;
import com.paperv.async.LoginTask;
import com.paperv.tabs_utils.GlobalState;

public class Splash extends Activity {
	public static final String PREFS_NAME = "MyPrefsFile";

	Context myContext = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		BugSenseHandler.initAndStartSession(this, "0da77729");
		// Hides the titlebar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.splash);

		GlobalState.getInstance().prefs = getSharedPreferences(PREFS_NAME, 0);

		try {

			if (GlobalState.getInstance().prefs
					.getBoolean("remember_me", false)) {
				LoginTask task = new LoginTask();
				task.myContext = myContext;
				task.showDialog = false;

				task.user_name = GlobalState.getInstance().prefs.getString(
						"user_name", "");
				task.password = GlobalState.getInstance().prefs.getString(
						"password", "");
				task.remember_me = GlobalState.getInstance().prefs.getBoolean(
						"remember_me", false);

				task.execute();
			}

			else {
				LinearLayout progressL = (LinearLayout) findViewById(R.id.progressbar);
				progressL.setVisibility(LinearLayout.GONE);

				Runnable r = new Runnable() {
					public void run() {
						finish();
						Intent intent = new Intent(Splash.this,
								StartActivity.class);
						startActivity(intent);
					}
				};

				Handler handler = new Handler();
				handler.postDelayed(r, 3000);

			}

		} catch (Exception e) {
			Log.d("helal", e.getMessage());
		}

	}
}