package com.paperv.www;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.paperv.async.LoginTask;
import com.paperv.core.PapervActivity;

public class StartActivity extends PapervActivity {

	@Override
	public void onCreateUI(Bundle savedInstanceState) {
		setContentView(R.layout.activity_start);
		
		final Button signin = (Button) findViewById(R.id.signin);
		signin.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				finish();
				Intent i = new Intent(mContext, Login.class);
				startActivityForResult(i, 700);
				overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
			}
		});

		final Button signup = (Button) findViewById(R.id.signup);
		signup.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Perform action on click
				finish();
				Intent i = new Intent(mContext, Register.class);
				startActivityForResult(i, 700);
				overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
			}
		});
		
		if (appInstance.isRememberMe()) {

			LoginTask task = new LoginTask();
			task.appInstance = this.appInstance;
			task.dialog = new ProgressDialog(mContext);
			task.execute();

		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}



}
