package paperv.core;

import java.util.ArrayList;

import paperv.models.MenuItem;
import paperv.network.DataConnector;
import paperv.tabs_adapters.MenuAdapter;
import paperv.tabs_fragments.About;
import paperv.tabs_fragments.Contact;
import paperv.tabs_fragments.Notification;
import paperv.tabs_fragments.Privacy;
import paperv.tabs_fragments.SearchList;
import paperv.tabs_fragments.Terms;
import paperv.tabs_utils.GlobalState;
import paperv.tabs_utils.Utils;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


public class MenuFragment extends Fragment implements OnItemClickListener {

	ListView listView;
	ArrayList<MenuItem> lstMenuItems;
	View vw_layout;

	EditText search_field;
	
	ImageView user_image;
	TextView user_name;
	GlobalState globalState = GlobalState.getInstance();
	DataConnector dataConnector = DataConnector.getInstance();
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
	 * android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (container == null) {
			// We have different layouts, and in one of them this
			// fragment's containing frame doesn't exist. The fragment
			// may still be created from its saved state, but there is
			// no reason to try to create its view hierarchy because it
			// won't be displayed. Note this is not needed -- we could
			// just run the code below, where we would create and return
			// the view hierarchy; it would just never be used.
			return null;
		}
		vw_layout = inflater.inflate(R.layout.fragment_menu, container, false);
		
		search_field = (EditText) vw_layout.findViewById(R.id.search_field);
		search_field.requestFocus();
		search_field.setOnTouchListener(foucsHandler);
		
		ImageButton search = (ImageButton) vw_layout.findViewById(R.id.btn_search);
		search.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Perform action on click
				
				if (! search_field.getText().toString().equals(""))
				{
					
					GetFriendsTask task = new GetFriendsTask(search_field.getText().toString());
	    			task.execute();
	    			
	    			search_field.setText("");
				}

			}
		});
		
		
		user_image = (ImageView) vw_layout.findViewById(R.id.avatar);
		if (globalState.user.getImage() != null)
		{
			user_image.setImageBitmap(globalState.user.getImage());
		}

		user_name = (TextView) vw_layout.findViewById(R.id.person_name);
		user_name.setText(globalState.user.getName());

		this.initialiseMenuItems(savedInstanceState);
		
		Utils.setFontAllView((ViewGroup) vw_layout);
		return vw_layout;
	}

	/**
	 * Step 2: Setup Menu Items
	 */
	private void initialiseMenuItems(Bundle args) {
		// get list view
		listView = (ListView) this.vw_layout.findViewById(R.id.lst_menu);

		lstMenuItems = new ArrayList<MenuItem>();

		addMenuItem(R.string.lbl_home, R.drawable.ico_home_2,null ,args);
		addMenuItem(R.string.lbl_notifications, R.drawable.notifications_2,Notification.class ,args);
//		addMenuItem(R.string.lbl_settings, R.drawable.settings_2,MenuActivity.class ,args);
		addMenuItem(R.string.lbl_about, R.drawable.about_2,About.class ,args);
		addMenuItem(R.string.lbl_privacy, R.drawable.privacy_2,Privacy.class ,args);
		addMenuItem(R.string.lbl_terms, R.drawable.terms_2,Terms.class ,args);
		addMenuItem(R.string.lbl_contact, R.drawable.contact_2,Contact.class ,args);
		addMenuItem(R.string.lbl_logout, R.drawable.logout_2,null ,args);
		

		// Assign the items to the list
		listView.setAdapter(new MenuAdapter(getActivity(), lstMenuItems));
		// Register item click listener
		listView.setOnItemClickListener(this);
	}

	@SuppressWarnings("rawtypes")
	private void addMenuItem(int labelID, int drawableId, Class cl, Bundle args) {
		MenuItem mnu = new MenuItem(labelID, drawableId, cl, args);
		lstMenuItems.add(mnu);
	}

	public void onItemClick(AdapterView<?> adp, View listview, int position,
			long id) {

		if (position == 0)
		{
			Intent i = new Intent(getActivity(), MainActivity.class);
        	startActivity(i); 
        	
        	getActivity().finish();
		}
		
		else if (position == 6)
		{
			SharedPreferences.Editor editor = globalState.prefs.edit();
			editor.putString("user_name", "");
			editor.putString("password", "");
			editor.putBoolean("remember_me", false);
			editor.commit();
			
        	getActivity().finish();
		}
		
		else if (adp != null && adp.getAdapter() instanceof MenuAdapter) {
			MenuAdapter menuAdp = (MenuAdapter) adp.getAdapter();
			MenuItem itm = menuAdp.getItem(position);
			Fragment newContent = itm.get_fragment();

			if (newContent == null) {

				newContent = Fragment.instantiate(getActivity(), itm
						.get_class().getName(), itm.get_args());
				itm.set_fragment(newContent);
			}

			switchFragment(newContent);

		}
	}

	// the meat of switching the above fragment
	private void switchFragment(Fragment fragment) {
		if (getActivity() == null)
			return;

		if (getActivity() instanceof MainActivity) {
			MainActivity mActivity = (MainActivity) getActivity();
			mActivity.switchContent(fragment);
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
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		setUserVisibleHint(true);
	}
	
	
	
	
	
	//======================================================================
	
	
	
	private class GetFriendsTask extends AsyncTask<Void, Void, Void> {

		boolean result;
		ProgressDialog dialog = null;
		
		String data;
		
		public GetFriendsTask(String data)
		{
			this.data = data;
		}

		@Override
		protected void onPreExecute() {
			
			dialog = new ProgressDialog(getActivity());
			dialog.setTitle(" PaperV ");
		
			dialog.setIcon(R.drawable.ico_dialog);
			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			dialog.setCancelable(false);
			dialog.setMessage("Loading search results ...");
			dialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {

			try {
				
				result = dataConnector.getFriends(data);
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
			
			if (this.result)
			{
				
				Fragment searchFragment = new SearchList();
				switchFragment(searchFragment);
				
			}
			
			else
			{
				
			}
			
		}

	}

}
