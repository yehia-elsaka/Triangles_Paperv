package com.paperv.async;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;

import com.paperv.core.CacheManager;
import com.paperv.core.PapervActivity;
import com.paperv.core.PapervApp;
import com.paperv.network.DataConnector;
import com.paperv.www.MainActivity;
import com.paperv.www.R;

public class LoginTask extends AsyncTask<Void, Void, Boolean> {

	public ProgressDialog dialog = null;
	public PapervActivity activityInstance;
	PapervApp appInstance;
	public boolean showDialog = true;
	
	CacheManager globalState = CacheManager.getInstance();
	DataConnector dataConnector = DataConnector.getInstance();

	@Override
	protected void onPreExecute() {
		// dialog = new ProgressDialog(myContext);
		appInstance = activityInstance.appInstance;
		if (showDialog) {
			dialog.setTitle(" PaperV ");
			dialog.setIcon(R.drawable.ico_dialog);
			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			dialog.setCancelable(false);
			dialog.setMessage("Logging In ...");
			dialog.show();
		}

	}

	@Override
	protected Boolean doInBackground(Void... params) {

		try {

			return dataConnector.loginIn(appInstance.getUserName(),
					appInstance.getPassword());
			// result =true;
		} catch (Exception e) {
			return false;
		}

	}

	@Override
	protected void onPostExecute(Boolean result) {

		try {

			if (showDialog)
				dialog.dismiss();

		} catch (Exception e) {

		}
		if (result) {
			if (!appInstance.isRememberMe()) {
				appInstance.setUserName("");
				appInstance.setPassword("");
			}

			activityInstance.finish();
			Intent i = new Intent(appInstance.getApplicationContext(), MainActivity.class);
			activityInstance.startActivityForResult(i, 700);
			activityInstance.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

		} else {
			
			activityInstance.showLongToast("Username or Password Incorrect");

		}
	}

}