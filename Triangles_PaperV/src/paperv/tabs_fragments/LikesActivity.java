package paperv.tabs_fragments;

import java.util.ArrayList;

import paperv.core.MainActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import paperv.core.R;
import paperv.models.LikeStory;
import paperv.models.StoryItem;
import paperv.models.StoryList;
import paperv.tabs_adapters.ExploreAdapter;
import paperv.tabs_adapters.LikesAdapter;
import paperv.tabs_utils.GlobalState;
import paperv.tabs_utils.Utils;


public class LikesActivity extends Fragment  {

	ListView listView;
	int lastIndex = -1;
	ArrayList<LikeStory> lstStories;
	View vw_layout;
	
	GlobalState globalState = GlobalState.getInstance();
	

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
		
		MainActivity.page_title.setText("Likes");

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
		vw_layout = inflater.inflate(R.layout.activity_likes, container, false);

		
		// get list view
		listView = (ListView) this.vw_layout.findViewById(R.id.lst_stories);
		lstStories = globalState.like_list;

		LikesAdapter likeAdapter = new LikesAdapter(getActivity(), lstStories);
		listView.setAdapter(likeAdapter);
		

		

		Utils.setFontAllView((ViewGroup) vw_layout);

		return vw_layout;
	}

	
	
	
	
	

}
