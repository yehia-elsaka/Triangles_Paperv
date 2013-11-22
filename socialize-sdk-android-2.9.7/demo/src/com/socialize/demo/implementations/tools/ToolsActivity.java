/*
 * Copyright (c) 2012 Socialize Inc.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.socialize.demo.implementations.tools;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.socialize.SocializeTools;
import com.socialize.demo.DemoActivity;
import com.socialize.demo.R;


/**
 * @author Jason Polites
 *
 */
public class ToolsActivity extends DemoActivity {

	@Override
	protected void onCreate() {

		setContentView(R.layout.tools);

		Button btnLocation = (Button) findViewById(R.id.btnSend);
		
		btnLocation.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!SocializeTools.sendExternalLogs(ToolsActivity.this)) {
					Toast.makeText(ToolsActivity.this, "No logs to send", Toast.LENGTH_SHORT).show();
				}
			}
		});

		Button btnClear = (Button) findViewById(R.id.btnClear);
		
		btnClear.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SocializeTools.deleteExternalLogs(ToolsActivity.this);
				Toast.makeText(ToolsActivity.this, "Logs cleared", Toast.LENGTH_SHORT).show();
			}
		});		
	}
}
