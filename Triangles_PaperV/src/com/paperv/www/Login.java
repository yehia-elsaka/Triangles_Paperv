package com.paperv.www;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.paperv.core.PapervActivity;

public class Login extends PapervActivity {

	public static String user_name = "";
	public static String password = "";
	EditText user_name_field;
	EditText password_field;
	CheckBox remember_me;
	TextView forgot_password;

	public void onCreateUI(android.os.Bundle savedInstanceState) {
		setContentView(R.layout.login);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

		TextView page_title = (TextView) findViewById(R.id.page_title);
		page_title.setText("Sign In");

		user_name_field = (EditText) findViewById(R.id.user_name);
		password_field = (EditText) findViewById(R.id.password);
		remember_me = (CheckBox) findViewById(R.id.remember_me);
		forgot_password = (TextView) findViewById(R.id.forget_pass);
		forgot_password.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(mContext, ForgotPasswordActivity.class);
				startActivityForResult(i, 700);
				overridePendingTransition(R.anim.slide_in_right,
						R.anim.slide_out_left);
			}
		});

		final Button sign_in = (Button) findViewById(R.id.signin);
		sign_in.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				user_name = user_name_field.getEditableText().toString().replaceAll(" ", "%20");
				password = password_field.getEditableText().toString().replaceAll(" ", "%20");
				LoginTask task = new LoginTask();
				task.execute();

			}
		});

	}
	
	
	
	
	
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
		Intent i = new Intent(mContext, StartActivity.class);
		startActivityForResult(i, 700);
		overridePendingTransition(R.anim.slide_in_left,
				R.anim.slide_out_right);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}

	public class LoginTask extends AsyncTask<Void, Void, Boolean> {

		ProgressDialog dialog = null;

		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(mContext);
			dialog.setTitle(" PaperV ");
			dialog.setIcon(R.drawable.ico_dialog);
			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			dialog.setCancelable(false);
			dialog.setMessage("Logging In ...");
			dialog.show();
		}

		@Override
		protected Boolean doInBackground(Void... params) {

			try {

				return dataConnector.loginIn(user_name, password);
				// result =true;
			} catch (Exception e) {
				return false;
			}

		}

		@Override
		protected void onPostExecute(Boolean result) {

			try {
				dialog.dismiss();
			} catch (Exception e) {

			}
			if (result) {

				if (remember_me.isChecked()){
					appInstance.setUserName(user_name);
					appInstance.setPassword(password);
					appInstance.setRememberMe(true);
				}
				else
				{
					appInstance.setUserName("");
					appInstance.setPassword("");
					appInstance.setRememberMe(false);
				}
				finish();
				Intent i = new Intent(mContext, MainActivity.class);
				startActivityForResult(i, 700);
				overridePendingTransition(R.anim.slide_in_right,
						R.anim.slide_out_left);

			} else {
				showLongToast("Username or Password Incorrect");
			}
		}

	}

}
