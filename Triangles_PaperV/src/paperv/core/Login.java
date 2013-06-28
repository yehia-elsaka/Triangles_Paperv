package paperv.core;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import paperv.core.R;
import paperv.network.DataConnector;
import paperv.tabs_utils.GlobalState;



public class Login extends Activity {
	
	Activity myActivity = this;
	String user_name = "";
	String password = "";
	EditText user_name_field;
	EditText password_field;
	CheckBox remember_me;
	Context myContext = this;
	
	
	TextView forgot_password;
	
	
	
	GlobalState globalState = GlobalState.getInstance();
	DataConnector dataConnector = DataConnector.getInstance();
	SharedPreferences prefs;
	public static final String PREFS_NAME = "MyPrefsFile";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		
		
		TextView page_title = (TextView) findViewById(R.id.page_title);
		page_title.setText("Sign In");
		
        
        user_name_field = (EditText) findViewById(R.id.user_name);
		password_field = (EditText) findViewById(R.id.password);
		remember_me = (CheckBox) findViewById(R.id.remember_me);
		prefs = getSharedPreferences(PREFS_NAME, 0);
		
		forgot_password = (TextView) findViewById(R.id.forget_pass);
		forgot_password.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
				Intent i = new Intent(myContext, ForgotPasswordActivity.class);
            	startActivityForResult(i, 700); 
        		overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

			}
		});
        
		

		
		if (prefs.getBoolean("remember_me", false)) {
			remember_me.setChecked(true);
			user_name = prefs.getString("user_name", "");
			password = prefs.getString("password", "");

			LoginTask task = new LoginTask();
			task.execute();
		}
        

		final Button sign_in = (Button) findViewById(R.id.signin);
		sign_in.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				user_name = user_name_field.getEditableText().toString();
				password = password_field.getEditableText().toString();

				if (remember_me.isChecked()) {
					SharedPreferences.Editor editor = prefs.edit();
					editor.putString("user_name", user_name);
					editor.putString("password", password);
					editor.putBoolean("remember_me", true);
					editor.commit();

				} else {
					SharedPreferences.Editor editor = prefs.edit();
					editor.putString("user_name", "");
					editor.putString("password", "");
					editor.putBoolean("remember_me", false);
					editor.commit();
				}

				LoginTask task = new LoginTask();
				task.execute();
				

			}
		});
        
        
        
	}
	
	
	
	
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
	
	
	
	private class LoginTask extends AsyncTask<Void, Void, Void> {

		boolean result;
		ProgressDialog dialog = null;

		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(myContext);
			dialog.setTitle(" PaperV ");
		
			dialog.setIcon(R.drawable.ico_dialog);
			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			dialog.setCancelable(false);
			dialog.setMessage("Logging In ...");
			dialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {

			try {
				
				result = dataConnector.loginIn(user_name, password);
//				result =true;
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
				
				finish();
				Intent i = new Intent(myContext, MainActivity.class);
            	startActivityForResult(i, 700); 
        		overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        		
			} else {
				Toast.makeText(myContext, "Username or Password Incorrect", 3000).show();
			}
		}

	}

	
	
	
	

}







