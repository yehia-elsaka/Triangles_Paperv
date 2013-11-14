package com.paperv.www;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.paperv.async.LoginTask;
import com.paperv.core.CacheManager;
import com.paperv.core.Constants;
import com.paperv.core.PapervActivity;
import com.paperv.network.DataConnector;

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
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		TextView page_title = (TextView) findViewById(R.id.page_title);
		page_title.setText("Sign Up");
		fullname_field = (EditText)findViewById(R.id.full_name_field);
		username_field = (EditText)findViewById(R.id.user_name_field);
        email_field = (EditText)findViewById(R.id.email_field);
        password_field = (EditText)findViewById(R.id.password_field);
		Button create = (Button)findViewById(R.id.register);
        create.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				fullname = fullname_field.getEditableText().toString().replaceAll(" ", "%20");
				username = username_field.getEditableText().toString().replaceAll(" ", "%20");
				email = email_field.getEditableText().toString().replaceAll(" ", "%20");
				password = password_field.getEditableText().toString().replaceAll(" ", "%20");
				try {		
					RegTask task = new RegTask();
					task.execute();
					
				} catch (Exception e) {
					Log.d(Constants.TAG, e.getMessage());
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
		overridePendingTransition(R.anim.slide_in_left,
				R.anim.slide_out_right);
	}
	
	
	
	private class RegTask extends AsyncTask<Void, Void, Void> {

		boolean result;
		ProgressDialog dialog = null;
		
		boolean user_exist = false;

		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(mContext);
			dialog.setTitle("PaperV");
			dialog.setIcon(R.drawable.ico_dialog);
			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			dialog.setCancelable(false);
			dialog.setMessage("Registering ...");
			dialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {

			try {
				
				DataConnector.getInstance().getFriends(username_field.getText().toString());
				if (CacheManager.getInstance().friends_list.size() != 0)
					{
						user_exist = true;
						result = false;
					}
				else
					result = DataConnector.getInstance().register(fullname, username, password, email, image);
				
			} catch (Exception e) {

				e.printStackTrace();
			}

			return null;

		}

		@Override
		protected void onPostExecute(Void result) {
			try{
				dialog.dismiss();
			}
			catch(Exception e){
				
			}
			if (this.result) {
				
				LoginTask task = new LoginTask();
				task.dialog = new ProgressDialog(mContext);
				task.activityInstance = Register.this;
				task.execute();
				
			} else {
				if (user_exist)
					showLongToast("Registration Failed ... User Name Already Exist");
				else
					showLongToast("Registration Failed ...");
			}
		}

	}


}