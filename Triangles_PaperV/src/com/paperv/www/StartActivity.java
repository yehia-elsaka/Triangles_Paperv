package com.paperv.www;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.bugsense.trace.BugSenseHandler;
import com.paperv.www.R;
import com.paperv.async.LoginTask;
import com.paperv.tabs_utils.GlobalState;

public class StartActivity extends Activity {

	Context myContext = this;
	public static final String PREFS_NAME = "MyPrefsFile";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		BugSenseHandler.initAndStartSession(myContext, "0da77729");
		setContentView(R.layout.activity_start);
		GlobalState.getInstance().prefs = getSharedPreferences(PREFS_NAME, 0);

		final Button signin = (Button) findViewById(R.id.signin);
		signin.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Perform action on click

				finish();
				Intent i = new Intent(myContext, Login.class);
				startActivityForResult(i, 700);
				overridePendingTransition(R.anim.slide_in_right,
						R.anim.slide_out_left);

			}
		});

		final Button signup = (Button) findViewById(R.id.signup);
		signup.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Perform action on click

				finish();
				Intent i = new Intent(myContext, Register.class);
				startActivityForResult(i, 700);
				overridePendingTransition(R.anim.slide_in_right,
						R.anim.slide_out_left);

			}
		});
		
		if (GlobalState.getInstance().prefs.getBoolean("remember_me", false)) {

			LoginTask task = new LoginTask();
			task.myContext = myContext;
			task.dialog = new ProgressDialog(myContext);
			task.user_name = GlobalState.getInstance().prefs.getString(
					"user_name", "");
			task.password = GlobalState.getInstance().prefs.getString(
					"password", "");
			task.remember_me = GlobalState.getInstance().prefs.getBoolean(
					"remember_me", false);

			task.execute();

		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/*@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		if (GlobalState.getInstance().is_logout) {
			finish();
		}
	}*/

}
