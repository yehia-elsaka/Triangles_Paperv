package paperv.tabs_fragments;

import java.util.ArrayList;

import paperv.core.MainActivity;
import paperv.core.R;
import paperv.lazy_adapter_utils.ImageLoader;
import paperv.models.Friend;
import paperv.network.DataConnector;
import paperv.tabs_utils.GlobalState;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SearchList extends Fragment{
	
	
	GlobalState globalState = GlobalState.getInstance();
	DataConnector dataConnector = DataConnector.getInstance();
	
	Button follow_user;
	
	ArrayList<Friend> lstFriends;
	
	ImageLoader imageLoader; 
	View view;
	
	int i = 0; // index
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		MainActivity.page_title.setText("Find Friends");
		
		imageLoader=new ImageLoader(getActivity().getApplicationContext());
		
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
		
		LinearLayout theLayout = (LinearLayout) inflater.inflate(
				R.layout.search_list, container, false);
		
		
		LinearLayout list = (LinearLayout) theLayout.findViewById(R.id.friends_list);
		

		for(i = 0; i < globalState.friends_list.size(); i++)
		{
			LayoutInflater _inflater = null;
			_inflater = getActivity().getLayoutInflater();
			final View view = _inflater.inflate(R.layout.row_friend, null);
			
			ImageView friendImage = (ImageView) view.findViewById(R.id.friend_image);
			TextView friendName = (TextView) view.findViewById(R.id.friend_name);
			TextView friendFullName = (TextView) view.findViewById(R.id.friend_full_name);
			
			friendName.setText(globalState.friends_list.get(i).getFriend_name());
			friendFullName.setText(globalState.friends_list.get(i).getFull_name());
			
			
			String userImage_url = globalState.friends_list.get(i).getFriend_image();
			
			if(!userImage_url.equals(""))
				imageLoader.DisplayImage(userImage_url, getActivity(), friendImage);
			
			view.setId(i);
			
			follow_user = (Button) view.findViewById(R.id.follow_user);
			follow_user.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	                // Perform action on click
	            	
	            	
	            	FollowTask task = new FollowTask(""+globalState.friends_list.get(view.getId()).getFriend_id());
	    			task.execute();
	    			
	            }
	        });
			
			list.addView(view);
		}
		
		return theLayout;
		
	}
	
	
	
	private class FollowTask extends AsyncTask<Void, Void, Void> {

		boolean result;
		ProgressDialog dialog = null;
		
		String friend_id;
		
		public FollowTask(String id)
		{
			this.friend_id = id;
		}

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected Void doInBackground(Void... params) {

			try {
				
				result = dataConnector.follow(friend_id);
				//result =true;
			} catch (Exception e) {

				e.printStackTrace();
			}

			return null;

		}

		@Override
		protected void onPostExecute(Void result) {

			if (this.result) {
				
				Toast.makeText(getActivity(), "Follow Done Successfully ... ", 3000).show();
				
				
			} else {
				
				Toast.makeText(getActivity(), "You already followed this user !!", 3000).show();
				
			}
		}

	}
		

}
