package com.paperv.async;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.widget.Toast;

import com.paperv.www.R;
import com.paperv.network.DataConnector;
import com.paperv.tabs_utils.GlobalState;
import com.paperv.www.MainActivity;


	public class LoginTask extends AsyncTask<Void, Void, Boolean> {
		
		public ProgressDialog dialog = null;
		public Context myContext;
		public String user_name;
		public String password;
		public boolean remember_me;
		GlobalState globalState = GlobalState.getInstance();
		DataConnector dataConnector = DataConnector.getInstance();
		
		@Override
		protected void onPreExecute() {
			//dialog = new ProgressDialog(myContext);
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

				if (remember_me){
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
					editor.putBoolean("remember_me", true);
					editor.commit();
				}
				((Activity)myContext).finish();
				Intent i = new Intent(myContext, MainActivity.class);
				((Activity)myContext).startActivityForResult(i, 700);
				((Activity)myContext).overridePendingTransition(R.anim.slide_in_right,
						R.anim.slide_out_left);

			} else {
				Toast.makeText(myContext, "Username or Password Incorrect",
						3000).show();
			}
		}

	}