package paperv.core;

import com.bugsense.trace.BugSenseHandler;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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
	public static String user_name = "";
	public static String password = "";
	EditText user_name_field;
	EditText password_field;
	CheckBox remember_me;
	Context myContext = this;

	TextView forgot_password;

	GlobalState globalState = GlobalState.getInstance();
	DataConnector dataConnector = DataConnector.getInstance();
	public static final String PREFS_NAME = "MyPrefsFile";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		BugSenseHandler.initAndStartSession(myContext, "0da77729");
		setContentView(R.layout.login);

		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

		TextView page_title = (TextView) findViewById(R.id.page_title);
		page_title.setText("Sign In");

		user_name_field = (EditText) findViewById(R.id.user_name);
		password_field = (EditText) findViewById(R.id.password);
		remember_me = (CheckBox) findViewById(R.id.remember_me);
		globalState.prefs = getSharedPreferences(PREFS_NAME, 0);

		forgot_password = (TextView) findViewById(R.id.forget_pass);
		forgot_password.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				Intent i = new Intent(myContext, ForgotPasswordActivity.class);
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
		// TODO Auto-generated method stub
		super.onBackPressed();
		
		finish();
		
		Intent i = new Intent(myContext, StartActivity.class);
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
			dialog = new ProgressDialog(myContext);
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
					Editor editor = globalState.prefs.edit();
					editor.putString("user_name", user_name);
					editor.putString("password", password);
					editor.putBoolean("remember_me", true);
					editor.commit();
				}
				else
				{
					Editor editor = globalState.prefs.edit();
					editor.remove("user_name");
					editor.remove("password");
					editor.putBoolean("remember_me", false);
					editor.commit();
				}
				finish();
				Intent i = new Intent(myContext, MainActivity.class);
				startActivityForResult(i, 700);
				overridePendingTransition(R.anim.slide_in_right,
						R.anim.slide_out_left);

			} else {
				Toast.makeText(myContext, "Username or Password Incorrect",
						3000).show();
			}
		}

	}

}
