package paperv.core;

import java.util.HashMap;

import paperv.network.DataConnector;
import paperv.tabs_fragments.ExploreActivity;
import paperv.tabs_fragments.GlideActivity;
import paperv.tabs_fragments.HomeActivity;
import paperv.tabs_fragments.LikesActivity;
import paperv.tabs_fragments.ProfileActivity;
import paperv.tabs_utils.GlobalState;
import paperv.tabs_utils.Utils;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;
import android.widget.TextView;
import android.widget.Toast;

import com.bugsense.trace.BugSenseHandler;
import com.slidingmenu.lib.SlidingMenu;

//

public class MainActivity extends FragmentActivity implements
		TabHost.OnTabChangeListener, OnClickListener{

	Context myContext = this;
	
	private SlidingMenu menu;
	private Fragment mContent;
	
	public static TextView page_title;

	EditText comment;
	
	private TabHost mTabHost;
	private HashMap<String, TabInfo> mapTabInfo = new HashMap<String, TabInfo>();
	private TabInfo mLastTab = null;

	GlobalState globalState = GlobalState.getInstance();
	DataConnector dataConnector = DataConnector.getInstance();
	
	private class TabInfo {
		private String _tag;
		private int _labelId;
		private int _drawableId;
		@SuppressWarnings("rawtypes")
		private Class _class;
		private Bundle _args;
		private Fragment _fragment;

		@SuppressWarnings("rawtypes")
		TabInfo(int labelID, int drawableId, Class cl, Bundle args) {
			this._tag = "tab" + labelID;
			this._labelId = labelID;
			this._drawableId = drawableId;
			this._class = cl;
			this._args = args;
		}

		public int get_labelId() {
			return _labelId;
		}

		public int get_drawableId() {
			return _drawableId;
		}

		@SuppressWarnings("rawtypes")
		public Class get_class() {
			return _class;
		}

		public Fragment get_fragment() {
			return _fragment;
		}

		public String get_tag() {
			return _tag;
		}

	}

	class TabFactory implements TabContentFactory {

		private final Context mContext;

		/**
		 * @param context
		 */
		public TabFactory(Context context) {
			mContext = context;
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see android.widget.TabHost.TabContentFactory#createTabContent(java.lang.String)
		 */
		public View createTabContent(String tag) {
			View v = new View(mContext);
			v.setMinimumWidth(0);
			v.setMinimumHeight(0);
			return v;
		}

	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		BugSenseHandler.initAndStartSession(myContext, "0da77729");
//		mContent = new HomeActivity();

		// set the Above View
		setContentView(R.layout.activity_main);
		
		
		comment = (EditText) findViewById(R.id.comment);
		comment.requestFocus();
		comment.setOnTouchListener(foucsHandler);
		
		page_title = (TextView) findViewById(R.id.page_title);
		page_title.setText("Home");
		
		ViewGroup vg = (ViewGroup)findViewById(R.id.main_root);
		

		// configure the SlidingMenu
		menu = new SlidingMenu(this);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.drawable.shadow);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.35f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		menu.setMenu(R.layout.menu_frame);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame, new MenuFragment()).commit();

		ImageButton btnList = (ImageButton) findViewById(R.id.btnImg_list);
		btnList.setOnClickListener(this);
		
		
		ImageButton btnReload = (ImageButton) findViewById(R.id.btn_reload);
		btnReload.setOnClickListener(this);
		
		// Step 2: Setup TabHost
		initialiseTabHost(savedInstanceState);
		
		if (globalState.open_profile_tab)
		{
			this.onTabChanged("tab" + R.string.lbl_profile);
			mTabHost.setOnTabChangedListener(this);
			
			mTabHost.setCurrentTab(4);
			
			globalState.open_profile_tab = false;
		}
		
		else if (savedInstanceState != null) {
			// set the tab as per the saved state
			mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
		}
		
		Utils.setFontAllView(vg);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onSaveInstanceState(android.os.Bundle)
	 */
	protected void onSaveInstanceState(Bundle outState) {
		outState.putString("tab", mTabHost.getCurrentTabTag()); // save the tab
																// selected
		super.onSaveInstanceState(outState);
	}

	/**
	 * Step 2: Setup TabHost
	 */
	private void initialiseTabHost(Bundle args) {
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup();

		addTab(R.string.lbl_home, R.drawable.ico_home_2,HomeActivity.class, args);
		addTab(R.string.lbl_explore, R.drawable.ico_search,ExploreActivity.class, args);
		addTab(R.string.lbl_glide, R.drawable.ico_glide_2,GlideActivity.class, args);
		addTab(R.string.lbl_likes, R.drawable.ico_likes_2, LikesActivity.class,args);
		addTab(R.string.lbl_profile, R.drawable.ico_person, ProfileActivity.class,args);
		

		// Default to first tab
		this.onTabChanged("tab" + R.string.lbl_home);
		//
		mTabHost.setOnTabChangedListener(this);
	}

	/**
	 * @param tabHost
	 * @param tabSpec
	 * @param clss
	 * @param args
	 */
	@SuppressWarnings("rawtypes")
	private void addTab(int labelID, int drawableId, Class cl, Bundle args) {

		TabInfo tabInfo = null;
		tabInfo = new TabInfo(labelID, drawableId, cl, args);
		this.mapTabInfo.put(tabInfo.get_tag(), tabInfo);

		TabHost.TabSpec spec = mTabHost.newTabSpec(tabInfo._tag);

		View tabIndicator = LayoutInflater.from(this).inflate(
				R.layout.tab_indicator,
				(ViewGroup) findViewById(android.R.id.tabs), false);
		TextView title = (TextView) tabIndicator.findViewById(R.id.title);
		title.setText(tabInfo.get_labelId());
		ImageView icon = (ImageView) tabIndicator.findViewById(R.id.icon);
		icon.setImageResource(tabInfo.get_drawableId());

		spec.setIndicator(tabIndicator);

		// Attach a Tab view factory to the spec
		spec.setContent(this.new TabFactory(this));
		String tag = spec.getTag();

		// Check to see if we already have a fragment for this tab, probably
		// from a previously saved state. If so, deactivate it, because our
		// initial state is that a tab isn't shown.
		tabInfo._fragment = this.getSupportFragmentManager().findFragmentByTag(
				tag);
		if (tabInfo._fragment != null && !tabInfo._fragment.isDetached()) {
			FragmentTransaction ft = this.getSupportFragmentManager()
					.beginTransaction();
			ft.detach(tabInfo._fragment);
			ft.commit();
			this.getSupportFragmentManager().executePendingTransactions();
		}

		mTabHost.addTab(spec);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see android.widget.TabHost.OnTabChangeListener#onTabChanged(java.lang.String)
	 */
	public void onTabChanged(String tag) {
		TabInfo newTab = this.mapTabInfo.get(tag);
		if (mLastTab != newTab) {
			FragmentTransaction ft = this.getSupportFragmentManager()
					.beginTransaction();
			if (mLastTab != null) {
				if (mLastTab._fragment != null) {
					ft.detach(mLastTab._fragment);
				}
			}
			if (newTab != null) {
				if (newTab.get_fragment() == null) {
					newTab._fragment = Fragment.instantiate(this, newTab
							.get_class().getName(), newTab._args);
					ft.add(R.id.realtabcontent, newTab.get_fragment(),
							newTab._tag);
				} else {
					ft.attach(newTab.get_fragment());
				}
			}

			mLastTab = newTab;
			ft.commit();
			this.getSupportFragmentManager().executePendingTransactions();
		}
	}
	
	
	public void switchContent(Fragment fragment) {
		mContent = fragment;
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, fragment).commit();
		menu.showContent();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.btnImg_list) {
			if (menu.isMenuShowing()) {
				menu.showContent();
			} else {
				menu.showMenu();
			}
		}
		
		
		if (v.getId() == R.id.btn_reload) {
			
			ReloadTask task = new ReloadTask();
			task.execute();
		}
		
	}
	
	OnTouchListener foucsHandler = new OnTouchListener() {
	    @Override
	    public boolean onTouch(View arg0, MotionEvent event) {
	        // TODO Auto-generated method stub
	        arg0.requestFocusFromTouch();
	            return false;
	    }
	};
	
	
	
	
	
	
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		
		globalState.is_logout = true;
		
		super.onBackPressed();
	}







	private class ReloadTask extends AsyncTask<Void, Void, Void> {

		boolean result;
		ProgressDialog dialog = null;

		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(myContext);
			dialog.setTitle(" PaperV ");
		
			dialog.setIcon(R.drawable.ico_dialog);
			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			dialog.setCancelable(false);
			dialog.setMessage("Reloading data ...");
			dialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {

			try {
				
				globalState.feed_list.clear();
				globalState.explore_list.clear();
				globalState.like_list.clear();
				globalState.notification_list.clear();
				globalState.userStories_list.clear();
				
				// load all data
				dataConnector.getExploreFeed();
				dataConnector.getHomeFeed();
				dataConnector.getUserStories();
				dataConnector.getFollowers(globalState.user.getId());
				dataConnector.getFollowing(globalState.user.getId());
				dataConnector.getAllNotification();
				dataConnector.getAllLikes();
				
//				result = dataConnector.loginIn(user_name, password);
				result =true;
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
				
				Toast.makeText(myContext, "Loading done ..,", 3000).show();
				
			} else {
				Toast.makeText(myContext, "Username or Password Incorrect", 3000).show();
			}
		}

	}
	
	
}
