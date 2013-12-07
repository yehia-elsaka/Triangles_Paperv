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
	String passwordConfirm;	
	String email;
	String image = "";
	
	EditText fullname_field;
	EditText username_field;
	EditText email_field;

	EditText password_confirmation_field;
	EditText password_field;

	@Override
	public void onCreateUI(Bundle savedInstanceState) {
		setContentView(R.layout.activity_register);
		
		fullname_field = (EditText) findViewById(R.id.full_name_field);
		username_field = (EditText) findViewById(R.id.user_name_field);
		email_field = (EditText) findViewById(R.id.email_field);
		password_field = (EditText) findViewById(R.id.password_field);
		password_confirmation_field = (EditText) findViewById(R.id.password_confirm_field);
		
		Button create = (Button) findViewById(R.id.reg_btn);
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
				passwordConfirm = password_confirmation_field.getEditableText().toString()
						.replaceAll(" ", "%20");
				
				if(password.equalsIgnoreCase(passwordConfirm))
				{
					apiHandler.register(Register.this, username, password, fullname, email, "Signing up ... ");
				}
				else
					showLongToast("Password confirmation doesn't match!");
			}

		});

	

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}

}