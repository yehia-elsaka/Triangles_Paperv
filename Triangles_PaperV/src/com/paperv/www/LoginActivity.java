package com.paperv.www;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.paperv.core.PapervActivity;

public class LoginActivity extends PapervActivity implements OnClickListener {

	String user_name = "";
	String password = "";

	EditText user_name_field;
	EditText password_field;
	CheckBox remember_me;
	TextView forgot_password;
	Button loginBtn;
	Button facebookBtn;
	Button twitterBtn;
	ImageView logo;
	ImageView logotxt;
	TextView or;
	LinearLayout socialPanel;
	
	@Override
	public void onCreateUI(Bundle savedInstanceState) {
		setContentView(R.layout.activity_login);
		user_name_field = (EditText) findViewById(R.id.email_field);
		password_field = (EditText) findViewById(R.id.password_field);
		loginBtn = (Button) findViewById(R.id.login_btn);
		loginBtn.setOnClickListener(this);
		twitterBtn = (Button)findViewById(R.id.twitter_btn);
		facebookBtn = (Button)findViewById(R.id.facebook_btn);
		logo = (ImageView)findViewById(R.id.logo);
		logotxt = (ImageView)findViewById(R.id.logo_txt);
		or = (TextView)findViewById(R.id.or);
		socialPanel = (LinearLayout)findViewById(R.id.social_panel);
		remember_me = (CheckBox)findViewById(R.id.remember_me_btn);
		// === Dynamic dimenstions
		logo.getLayoutParams().width = screenHeight / 5;
		logo.getLayoutParams().height = screenHeight / 5;		
		
		logotxt.getLayoutParams().width = screenHeight / 5;
		logotxt.getLayoutParams().height = screenHeight / 5;		
		
		/*user_name_field.getLayoutParams().height = (int)(screenHeight / 12);
		password_field.getLayoutParams().height = (int)(screenHeight / 12);
			*/
		loginBtn.getLayoutParams().height = (int)(screenHeight / 12);
	
		twitterBtn.getLayoutParams().width = (int)(screenWidth / 2.5);
		twitterBtn.getLayoutParams().height = (int)(screenHeight / 12);
		
		facebookBtn.getLayoutParams().width = (int)(screenWidth / 2.5);
		facebookBtn.getLayoutParams().height = (int)(screenHeight / 12);
		
		or.getLayoutParams().width = (int)(screenHeight / 12);
		or.getLayoutParams().height = (int)(screenHeight / 12);
		
		socialPanel.getLayoutParams().height = (screenHeight / 12) + 25;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_btn:
			user_name = user_name_field.getEditableText().toString()
					.replaceAll(" ", "%20");
			password = password_field.getEditableText().toString()
					.replaceAll(" ", "%20");
			apiHandler.login(LoginActivity.this, true, user_name, password,
					"Verifying user information", remember_me.isChecked());
			break;

		default:
			break;
		}
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}


}
