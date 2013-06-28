package paperv.core;



import paperv.core.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class StartActivity extends Activity {
	
	Context myContext = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		
		
		final Button signin = (Button) findViewById(R.id.signin);
        signin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	
            	Intent i = new Intent(myContext, Login.class);
            	startActivityForResult(i, 700); 
        		overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        		
            }
        });
        
        final Button signup = (Button) findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	
            	Intent i = new Intent(myContext, Register.class);
            	startActivityForResult(i, 700); 
        		overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        		
            }
        });
		
		
	}
	
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
