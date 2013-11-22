package com.paperv.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.paperv.core.PapervActivity;
import com.paperv.www.R;

public abstract class APIConnector extends AsyncTask<Void, Void, Boolean> {

	public PapervActivity activityInstance;
	public boolean showDialog = true;
	ProgressDialog dialog = null;
	String url;
	String dialogText;

	public APIConnector(PapervActivity activityInstance, boolean showDialog,
			String url, String dialogText) {
		this.activityInstance = activityInstance;
		this.showDialog = showDialog;
		this.url = url;
		this.dialogText = dialogText;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if (showDialog) {
			dialog = new ProgressDialog(activityInstance);
			dialog.setTitle(" PaperV ");
			dialog.setIcon(R.drawable.ico_dialog);
			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			dialog.setCancelable(false);
			dialog.setMessage(dialogText);
			dialog.show();
		}
	}

	@Override
	protected Boolean doInBackground(Void... params) {
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httpPOST = new HttpPost(url);
			HttpResponse response = httpclient.execute(httpPOST);
			HttpEntity entity = response.getEntity();
			String result = "";
			if (entity != null) {
				InputStream instream = entity.getContent();
				result = convertStreamToString(instream).replace("/r/n", "").replace("/n", "").trim();
				instream.close();
			}
			JSONArray jsonArr = new JSONArray(result);
			return custom_doInBackground(jsonArr);
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		custom_onPostExecute(result);
		if(dialog != null)
			dialog.dismiss();
	}

	// ======= Abstract Functions =======
	public abstract boolean custom_doInBackground(JSONArray json);

	public abstract void custom_onPostExecute(boolean result);

	// ======= Utility Functions =======
	public String convertStreamToString(InputStream is) {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	public Bitmap downloadBitmap(String bitmap_url) {
		String url = bitmap_url;
		Bitmap image = null;
		try {
			InputStream in = new java.net.URL(url).openStream();	
			image = BitmapFactory.decodeStream(in);
			in.close();
		} catch (Exception e) {
			return null;
		}
		return image;
	}
}
