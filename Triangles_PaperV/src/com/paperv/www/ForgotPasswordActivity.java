package com.paperv.www;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.paperv.core.PapervActivity;




public class ForgotPasswordActivity extends PapervActivity {
	
	EditText email_field;
	String email;
	
	@Override
	public void onCreateUI(Bundle savedInstanceState) {
		setContentView(R.layout.forgot_pass);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		TextView page_title = (TextView) findViewById(R.id.page_title);
		page_title.setText("Forgot Password");		
		email_field = (EditText) findViewById(R.id.forget_mail);
		final Button sign_in = (Button) findViewById(R.id.signin);
		sign_in.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				email = email_field.getEditableText().toString();
				ForgotTask task = new ForgotTask();
				task.execute();
			}
		});
        
	}
	
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
	
	
	
	private class ForgotTask extends AsyncTask<Void, Void, Void> {

		boolean result;
		ProgressDialog dialog = null;

		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(mContext);
			dialog.setTitle(" PaperV ");
			dialog.setIcon(R.drawable.ico_dialog);
			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			dialog.setCancelable(false);
			dialog.setMessage("Submitting eMail ...");
			dialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {

			try {
				
				result = dataConnector.forgot(email);
				//result =true;
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
				Intent i = new Intent(mContext, Login.class);
            	startActivityForResult(i, 700); 
        		overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        		
			} else {
				showLongToast("e-mail Incorrect");
			}
		}

	}

	

}







