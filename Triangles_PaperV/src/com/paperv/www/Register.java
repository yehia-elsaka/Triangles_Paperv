package com.paperv.www;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.paperv.core.PapervActivity;

public class Register extends PapervActivity {

	String fullname;
	String username;
	String password;
	String email;
	String image = "";
	EditText fullname_field;
	EditText username_field;
	EditText password_field;
	EditText email_field;

	@Override
	public void onCreateUI(Bundle savedInstanceState) {
		setContentView(R.layout.signup);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		TextView page_title = (TextView) findViewById(R.id.page_title);
		page_title.setText("Sign Up");
		fullname_field = (EditText) findViewById(R.id.full_name_field);
		username_field = (EditText) findViewById(R.id.user_name_field);
		email_field = (EditText) findViewById(R.id.email_field);
		password_field = (EditText) findViewById(R.id.password_field);
		Button create = (Button) findViewById(R.id.register);
		create.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				fullname = fullname_field.getEditableText().toString()
						.replaceAll(" ", "%20");
				username = username_field.getEditableText().toString()
						.replaceAll(" ", "%20");
				email = email_field.getEditableText().toString()
						.replaceAll(" ", "%20");
				password = password_field.getEditableText().toString()
						.replaceAll(" ", "%20");
				try {
					apiHandler.register(Register.this, username, password, fullname, email, "Registering user info");
				} catch (Exception e) {
					showLongToast("Register request failed");
				}
			}

		});

		TextView terms = (TextView) findViewById(R.id.terms);
		terms.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(mContext, AppTermsActivity.class);
				startActivityForResult(i, 700);
				overridePendingTransition(R.anim.slide_in_right,
						R.anim.slide_out_left);

			}

		});

		TextView privacy = (TextView) findViewById(R.id.privacy);
		privacy.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				Intent i = new Intent(mContext, AppPrivacyActivity.class);
				startActivityForResult(i, 700);
				overridePendingTransition(R.anim.slide_in_right,
						R.anim.slide_out_left);

			}

		});

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish();
		Intent i = new Intent(mContext, StartActivity.class);
		startActivityForResult(i, 700);
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}

}