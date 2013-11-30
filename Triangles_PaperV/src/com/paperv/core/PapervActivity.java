package com.paperv.core;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.paperv.api.APIHandler;
import com.paperv.network.DataConnector;
import com.socialize.Socialize;
import com.socialize.api.SocializeSession;
import com.socialize.api.action.share.SocialNetworkShareListener;
import com.socialize.entity.Entity;
import com.socialize.error.SocializeException;
import com.socialize.listener.SocializeAuthListener;
import com.socialize.networks.SocialNetwork;
import com.socialize.networks.facebook.FacebookUtils;

public abstract class PapervActivity extends FragmentActivity {

	public PapervApp appInstance;
	public Context mContext = this;
	public CacheManager cache = CacheManager.getInstance();
	public DataConnector dataConnector = DataConnector.getInstance();
	public APIHandler apiHandler = APIHandler.getInstance();

	public int screenWidth;
	public int screenHeight;

	@Override
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// BugSenseHandler.initAndStartSession(this, "0da77729");\
		Socialize.onCreate(this, savedInstanceState);

		appInstance = (PapervApp) getApplication();
		screenWidth = getWindowManager().getDefaultDisplay().getWidth();
		screenHeight = getWindowManager().getDefaultDisplay().getHeight();
		onCreateUI(savedInstanceState);
	};

	public abstract void onCreateUI(android.os.Bundle savedInstanceState);

	public void showLongToast(String text) {
		Toast.makeText(mContext, text, Toast.LENGTH_LONG).show();
	}

	public Point getFeedDimensions() {
		Point p = new Point();
		if (screenWidth > Constants.SMALL_SCREEN_SIZE) {
			int w = (int) (cache.screenWidth / 2.1);
			int h = (int) (w * 1.2);
			p = new Point(w, h);
		} else {
			int w = (int) (cache.screenWidth / 1.1);
			int h = (int) (w * 1.2);
			p = new Point(w, h);
		}

		return p;
	}

	public void linkAndPost(final String msg,final String link,final String name) {
		if (FacebookUtils.isLinkedForWrite(this)) {
			postToFb(msg, link, name);
		}
		else {
			FacebookUtils.linkForWrite(this, new SocializeAuthListener() {
				
				@Override
				public void onCancel() {
					showLongToast("Link Canceled");
				}
				
				@Override
				public void onAuthSuccess(SocializeSession session) {
					postToFb(msg, link, name);
				}
				
				@Override
				public void onAuthFail(SocializeException error) {
					showLongToast("Auth Failed");
				}
				
				@Override
				public void onError(SocializeException error) {
					showLongToast("Link Error");
				}
			});	
		}
	}

	public void postToFb(String msg, String link, String name) {
		String graphPath = "me/links";

		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("message", msg);
		postData.put("link", link);
		postData.put("name", name);

//		FacebookUtils.post(this, graphPath, postData,
//				new SocialNetworkPostListener()
//		
		
		Entity entity = Entity.newInstance(link, name);
		
		// The "this" argument refers to the current Activity
		FacebookUtils.postEntity(this, entity, msg, new SocialNetworkShareListener()
		{

					@Override
					public void onNetworkError(Activity context,
							SocialNetwork network, Exception error) {
						showLongToast("network error");
					}

					@Override
					public void onCancel() {
						showLongToast("request canceled");
					}

					@Override
					public void onAfterPost(Activity parent,
							SocialNetwork socialNetwork,
							JSONObject responseObject) {
						showLongToast("finished");

					}
				});

	}

	@Override
	protected void onPause() {
		super.onPause();
		Socialize.onPause(this);
	}

	@Override
	protected void onResume() {
		super.onResume();

		Socialize.onResume(this);
	}

	@Override
	protected void onDestroy() {
		Socialize.onDestroy(this);
		super.onDestroy();
	}
}
