package com.paperv.www;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.paperv.core.PapervActivity;

public class Splash extends PapervActivity {

	@Override
	public void onCreateUI(android.os.Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash);
		cache.screenWidth = getWindowManager().getDefaultDisplay().getWidth();
		cache.screenHeight = getWindowManager().getDefaultDisplay().getHeight();
		
		
		ImageView logo = (ImageView)findViewById(R.id.logo);
		ImageView logotxt = (ImageView)findViewById(R.id.logo_txt);
		
		logo.getLayoutParams().width = screenHeight / 4;
		logo.getLayoutParams().height = screenHeight / 4;		
		
		logotxt.getLayoutParams().width = screenHeight / 4;
		logotxt.getLayoutParams().height = screenHeight / 4;		
		try {
			if (appInstance.isRememberMe()) {
//				LoginTask task = new LoginTask();
//				task.showDialog = false;
//				task.activityInstance = this;
//				task.execute();
				
				apiHandler.login(Splash.this,false , appInstance.getUserName(), appInstance.getPassword(), "", appInstance.isRememberMe());
				
			}

			else {
				LinearLayout progressL = (LinearLayout) findViewById(R.id.progressbar);
				progressL.setVisibility(LinearLayout.GONE);
				Runnable r = new Runnable() {
					public void run() {
						finish();
						Intent intent = new Intent(Splash.this,LoginActivity.class);
						startActivity(intent);
					}
				};

				Handler handler = new Handler();
				handler.postDelayed(r, 3000);
			}

		} catch (Exception e) {
			Log.d(com.paperv.core.Constants.TAG, e.getMessage());
		}

	}

}