package paperv.core;

import com.bugsense.trace.BugSenseHandler;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.LoginFilter.PasswordFilterGMail;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import paperv.async.LoginTask;
import paperv.core.R;
import paperv.network.DataConnector;
import paperv.tabs_utils.GlobalState;

public class Register extends Activity {
	
	Context myContext = this;
	
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
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		BugSenseHandler.initAndStartSession(myContext, "0da77729");
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
			
		});
		
		
	}
	
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		
		finish();
		
		Intent i = new Intent(myContext, StartActivity.class);
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
			dialog = new ProgressDialog(myContext);
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
				if (GlobalState.getInstance().friends_list.size() != 0)
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
//				finish();
//				Intent i = new Intent(myContext, StartActivity.class);
//            	startActivityForResult(i, 700); 
//        		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        		
				LoginTask task = new LoginTask();
				task.dialog = new ProgressDialog(myContext);
				task.myContext = myContext;
				task.remember_me = true;
				task.user_name = username_field.getEditableText().toString().replaceAll(" ", "%20");
				task.password = password_field.getEditableText().toString().replaceAll(" ", "%20");
				task.execute();
				
			} else {
				if (user_exist)
					Toast.makeText(myContext, "Registration Failed ... User Name Already Exist", 3000)
							.show();
				else
					Toast.makeText(myContext, "Registration Failed ...", 3000)
					.show();
			}
		}

	}


}