package com.paperv.tabs_fragments;

import java.lang.reflect.Field;
import java.util.ArrayList;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.paperv.lazy_adapter_utils.ImageLoader;
import com.paperv.models.LikeStory;
import com.paperv.network.DataConnector;
import com.paperv.tabs_adapters.ImageAdapter;
import com.paperv.tabs_adapters.LikesAdapter;
import com.paperv.tabs_utils.DepthPageTransformer;
import com.paperv.tabs_utils.FixedSpeedScroller;
import com.paperv.tabs_utils.GlobalState;
import com.paperv.tabs_utils.Utils;
import com.paperv.www.MainActivity;
import com.paperv.www.R;

public class LikesActivity extends Fragment implements OnItemClickListener,
		OnClickListener {

	ListView listView;
	int lastIndex = -1;
	ArrayList<LikeStory> lstStories;
	ArrayList<LikeStory> currentStories;
	View vw_layout;

	View vw_master;
	View vw_detail;
	LinearLayout comments_list;

	GlobalState globalState = GlobalState.getInstance();
	DataConnector dataConnector = DataConnector.getInstance();

	// detail view
	TextView likesNumber;
	TextView commentsNumber;
	TextView reglideNumber;
	TextView userName, stroyName;
	ImageView userImage;
	ImageButton btnBack, btnLike, btnReglide;
	Button follow_user;
	ViewPager viewPager;

	// animation
	private Animation mSlideInLeft;
	private Animation mSlideOutRight;
	private Animation mSlideInRight;
	private Animation mSlideOutLeft;
	private Animation mFade;
	private Animation mSlideOutBottom;
	private Animation mSlideInBottom;
	private Animation mSlideOutTop;
	private Animation mSlideInTop;

	int current_story_id;
	int current_story_index;
	String current_video_url;

	public ImageLoader userImageLoader;
	public ImageLoader storyImageLoader;


	View tabs_bar;
	View comments_bar;

	EditText comment;
	ImageButton add_comment;

	boolean _isBack = true;
	boolean flag_loading = false;
	LikesAdapter likeAdapter;
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

		userImageLoader = new ImageLoader(getActivity().getApplicationContext());
		storyImageLoader = new ImageLoader(getActivity()
				.getApplicationContext());
		

		currentStories = new ArrayList<LikeStory>();

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
		
		this.vw_master = (View) vw_layout.findViewById(R.id.master);
		// get list view
		listView = (ListView) this.vw_master.findViewById(R.id.lst_stories);
		lstStories = globalState.like_list;
		

		LinearLayout tabs = (LinearLayout) getActivity().findViewById(
				R.id.tabs_bar);
		comments_bar = tabs.getChildAt(0);
		tabs_bar = tabs.getChildAt(1);

		comment = (EditText) comments_bar.findViewById(R.id.comment);
		comment.setText("");

		add_comment = (ImageButton) comments_bar.findViewById(R.id.add_comment);
		add_comment.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Perform action on click

				if (!comment.getEditableText().toString().equals("")) {
					CommentStoryTask task = new CommentStoryTask();
					task.execute();
				}

			}
		});

		this.vw_detail = (View) vw_layout.findViewById(R.id.detail);
		// get detail controls
		userImage = (ImageView) this.vw_detail.findViewById(R.id.user_image_2);
		userName = (TextView) this.vw_detail.findViewById(R.id.author_name);
		stroyName = (TextView) this.vw_detail.findViewById(R.id.story_name_2);
		likesNumber = (TextView) vw_detail.findViewById(R.id.likes_number_2);
		commentsNumber = (TextView) vw_detail
				.findViewById(R.id.comments_number_2);
		reglideNumber = (TextView) vw_detail
				.findViewById(R.id.reglides_number_2);

		follow_user = (Button) vw_detail.findViewById(R.id.follow_user);
		follow_user.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Perform action on click

				FollowTask task = new FollowTask();
				task.execute();

			}
		});


		comments_list = (LinearLayout) vw_detail
				.findViewById(R.id.comments_list);

		btnBack = (ImageButton) this.vw_detail.findViewById(R.id.btn_back);
		btnLike = (ImageButton) this.vw_detail.findViewById(R.id.like_story);
		btnReglide = (ImageButton) this.vw_detail
				.findViewById(R.id.reglide_story);

		btnBack.setOnClickListener(this);

		btnLike.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Perform action on click

				likeStoryTask task = new likeStoryTask();
				task.execute();

			}
		});

		btnReglide.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Perform action on click

				reglideStoryTask task = new reglideStoryTask();
				task.execute();

			}
		});



		viewPager = (ViewPager) this.vw_detail.findViewById(R.id.view_pager);
		viewPager.setPageTransformer(true, new DepthPageTransformer());
		
		
        try {
        	
			Field mScroller;
			mScroller = ViewPager.class.getDeclaredField("mScroller");

			mScroller.setAccessible(true);
			FixedSpeedScroller scroller = new FixedSpeedScroller(
					viewPager.getContext());
			// scroller.setFixedDuration(5000);
			mScroller.set(viewPager, scroller);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		

		this.vw_master.setVisibility(View.VISIBLE);
		this.vw_detail.setVisibility(View.GONE);

		this.tabs_bar.setVisibility(View.VISIBLE);
		this.comments_bar.setVisibility(View.GONE);

		
		

		if (lstStories.size() > 0)
			currentStories.add(lstStories.get(0));
		
		
		likeAdapter = new LikesAdapter(getActivity(), currentStories);
		listView.setAdapter(likeAdapter);
		listView.setOnItemClickListener(this);

		listView.setOnScrollListener(new OnScrollListener() {

	        public void onScrollStateChanged(AbsListView view, int scrollState) {


	        }

	        public void onScroll(AbsListView view, int firstVisibleItem,
	                int visibleItemCount, int totalItemCount) {
	        	
	        	if (currentStories.size() < lstStories.size())
		            if(firstVisibleItem+visibleItemCount == totalItemCount && totalItemCount!=0)
		            {
		                if(flag_loading == false)
		                {
		                    flag_loading = true;
		                    getCurrentStories();
		                }
		            }
	        }
	    });
		
		
		
		
		
		
		this.initAnimation();
		Utils.setFontAllView((ViewGroup) vw_layout);

		return vw_layout;
	}

	private void initAnimation() {
		// animation
		mSlideInLeft = AnimationUtils.loadAnimation(getActivity(),
				R.anim.push_left_in);
		mSlideOutRight = AnimationUtils.loadAnimation(getActivity(),
				R.anim.push_right_out);
		mSlideInRight = AnimationUtils.loadAnimation(getActivity(),
				R.anim.push_right_in);
		mSlideOutLeft = AnimationUtils.loadAnimation(getActivity(),
				R.anim.push_left_out);

		mFade = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);

		mSlideOutBottom = AnimationUtils.loadAnimation(getActivity(),
				R.anim.slide_out_bottom);

		mSlideInBottom = AnimationUtils.loadAnimation(getActivity(),
				R.anim.slide_in_bottom);

		mSlideOutTop = AnimationUtils.loadAnimation(getActivity(),
				R.anim.slide_out_top);

		mSlideInTop = AnimationUtils.loadAnimation(getActivity(),
				R.anim.slide_in_top);
	}

	public void onItemClick(AdapterView<?> adp, View listview, int position,
			long id) {
		this._isBack = false;

		current_story_index = position;

		if (adp != null && adp.getAdapter() instanceof LikesAdapter) {
			LikesAdapter newsAdp = (LikesAdapter) adp.getAdapter();
			LikeStory itm = (LikeStory) newsAdp.getItem(position);

			
			current_story_id = itm.getStory_id();
			
			for (int i = 0; i < globalState.explore_list.size(); i++)
			{
				if (current_story_id == globalState.explore_list.get(i).getStory_id())
					globalState.story_view = globalState.explore_list.get(i);
			}
			
			comments_list.removeAllViews();

			globalState.in_show_mode = true;
			MainActivity.menu.setSlidingEnabled(false);
			MainActivity.btnList.setClickable(false);
			MainActivity.btnList.setImageResource(R.drawable.ico_view3);

			getStoryViewTask task = new getStoryViewTask();
			task.execute();

		}
	}

	private void showView(boolean isBack) {
		try {

			if (isBack) {

				this.comments_bar.startAnimation(mSlideOutTop);
				this.comments_bar.setVisibility(View.GONE);

				this.tabs_bar.setVisibility(View.VISIBLE);
				this.tabs_bar.startAnimation(mSlideInBottom);

				this.vw_master.setVisibility(View.VISIBLE);
				this.vw_detail.setVisibility(View.GONE);
				this.vw_detail.startAnimation(mSlideOutRight);
				this.vw_master.startAnimation(mSlideInLeft);

			} else {
				this.vw_master.setVisibility(View.GONE);
				this.vw_detail.setVisibility(View.VISIBLE);
				this.vw_master.startAnimation(mSlideOutLeft);
				this.vw_detail.startAnimation(mSlideInRight);

				this.tabs_bar.startAnimation(mSlideOutBottom);
				this.tabs_bar.setVisibility(View.GONE);

				this.comments_bar.setVisibility(View.VISIBLE);
				this.comments_bar.startAnimation(mFade);

			}

		} catch (Exception e) {

		}
	}

	public void onBackPressed() {
		if (!this._isBack) {
			this._isBack = !this._isBack;
			showView(this._isBack);

			MainActivity.btnList.setImageResource(R.drawable.ico_list);
			globalState.in_show_mode = false;
			MainActivity.btnList.setClickable(true);
			MainActivity.menu.setSlidingEnabled(true);
			
			return;
		}
	}

	@Override
	public void onClick(View v) {
		onBackPressed();
	}

	// ====================================================================================

	
	
	private void getCurrentStories()
	{
		int counter = 0;
		int offset = 0 + currentStories.size();
		
		while (currentStories.size() < lstStories.size() && counter < 5)
		{
			counter++;
			
			currentStories.add(lstStories.get(offset));
			offset++;
		}
		
		flag_loading = false;
		likeAdapter.notifyDataSetChanged();
		
	}
	

	// ====================================================================================

	private class getStoryViewTask extends AsyncTask<Void, Void, Void> {

		boolean result;
		ProgressDialog dialog = null;

		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(getActivity());
			dialog.setTitle(" PaperV ");

			dialog.setIcon(R.drawable.ico_dialog);
			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			dialog.setCancelable(false);
			dialog.setMessage("Loading Story ...");
			dialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {

			try {

				result = dataConnector.getStoryView("" + current_story_id);
				// result =true;
			} catch (Exception e) {

				e.printStackTrace();
			}

			return null;

		}

		@Override
		protected void onPostExecute(Void result) {

			try {
				dialog.dismiss();
			} catch (Exception e) {

			}
			if (this.result) {
				

				ImageAdapter adapter = new ImageAdapter(getActivity(), globalState.story_view);
			    viewPager.setAdapter(adapter);
				

				userName.setText(globalState.story_view.getUser_name());
				stroyName.setText(globalState.story_view.getStory_name());

				likesNumber.setText(""
						+ globalState.story_view.getLikes_number());
				reglideNumber.setText(""
						+ globalState.story_view.getReglide_number());
				commentsNumber.setText(""
						+ globalState.story_view.getComments_number());

				String userImage_url = globalState.story_view.getUser_image();

				userImageLoader.DisplayImage(userImage_url, getActivity(),
						userImage);


				for (int i = 0; i < globalState.story_view.getComments().size(); i++) {
					LayoutInflater _inflater = null;
					_inflater = getActivity().getLayoutInflater();
					View view = _inflater
							.inflate(R.layout.layout_comment, null);

					ImageView comment_Image = (ImageView) view
							.findViewById(R.id.comment_user_image);
					TextView comment_user = (TextView) view
							.findViewById(R.id.comment_author_name);
					TextView comment_text = (TextView) view
							.findViewById(R.id.Comment_text);

					comment_user.setText(globalState.story_view.getComments()
							.get(i).getUser_name());
					comment_text.setText(globalState.story_view.getComments()
							.get(i).getCommentText());

					ImageLoader imageLoader = new ImageLoader(getActivity()
							.getApplicationContext());
					imageLoader.DisplayImage(globalState.story_view
							.getComments().get(i).getUser_image_url(),
							getActivity(), comment_Image);

					comments_list.addView(view);
				}

				showView(_isBack);

			} else {

				Toast.makeText(getActivity(), "Can't show this story !!", 3000)
						.show();

			}
		}

	}

	private class likeStoryTask extends AsyncTask<Void, Void, Void> {

		boolean result;
		ProgressDialog dialog = null;

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected Void doInBackground(Void... params) {

			try {

				result = dataConnector.likeStory("" + current_story_id);
				// result =true;
			} catch (Exception e) {

				e.printStackTrace();
			}

			return null;

		}

		@Override
		protected void onPostExecute(Void result) {

			if (this.result) {

				globalState.story_view.setLikes_number(globalState.story_view
						.getLikes_number() + 1);

				likesNumber.setText(""
						+ globalState.story_view.getLikes_number());

			} else {

				Toast.makeText(getActivity(),
						"You already liked this story !!", 3000).show();

			}
		}

	}

	private class CommentStoryTask extends AsyncTask<Void, Void, Void> {

		boolean result;
		ProgressDialog dialog = null;

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected Void doInBackground(Void... params) {

			try {

				result = dataConnector.commentStory(
						"" + current_story_id,
						comment.getEditableText().toString()
								.replaceAll(" ", "%20"));
				// result =true;
			} catch (Exception e) {

				e.printStackTrace();
			}

			return null;

		}

		@Override
		protected void onPostExecute(Void result) {

			if (this.result) {

				globalState.story_view
						.setComments_number(globalState.story_view
								.getComments_number() + 1);
				commentsNumber.setText(""
						+ globalState.story_view.getComments_number());

				LayoutInflater _inflater = null;
				_inflater = getActivity().getLayoutInflater();
				View view = _inflater.inflate(R.layout.layout_comment, null);

				ImageView comment_Image = (ImageView) view
						.findViewById(R.id.comment_user_image);
				TextView comment_user = (TextView) view
						.findViewById(R.id.comment_author_name);
				TextView comment_text = (TextView) view
						.findViewById(R.id.Comment_text);

				comment_user.setText(globalState.user.getName());
				comment_text.setText(comment.getEditableText().toString());

				comment_Image.setImageBitmap(globalState.user.getImage());

				comments_list.addView(view);

				comment.setText("");

			} else {

			}
		}

	}

	private class reglideStoryTask extends AsyncTask<Void, Void, Void> {

		boolean result;
		ProgressDialog dialog = null;

		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(getActivity());
			dialog.setTitle(" PaperV ");

			dialog.setIcon(R.drawable.ico_dialog);
			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			dialog.setCancelable(false);
			dialog.setMessage("Regliding ...");
			dialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {

			try {

				result = dataConnector.reglideStory("" + current_story_id,
						globalState.story_view.getOwner_id());
				// result =true;
			} catch (Exception e) {

				e.printStackTrace();
			}

			return null;

		}

		@Override
		protected void onPostExecute(Void result) {

			try {
				dialog.dismiss();
			} catch (Exception e) {

			}
			if (this.result) {

				globalState.story_view.setReglide_number(globalState.story_view
						.getReglide_number() + 1);

				reglideNumber.setText(""
						+ globalState.story_view.getReglide_number());
				
				Toast.makeText(getActivity(),
						"Regliding Done ...", 3000).show();

			} else {

				Toast.makeText(getActivity(),
						"You already reglided this story !!", 3000).show();

			}
		}

	}

	private class FollowTask extends AsyncTask<Void, Void, Void> {

		boolean result;
		ProgressDialog dialog = null;

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected Void doInBackground(Void... params) {

			try {

				result = dataConnector.follow(globalState.story_view
						.getOwner_id());
				// result =true;
			} catch (Exception e) {

				e.printStackTrace();
			}

			return null;

		}

		@Override
		protected void onPostExecute(Void result) {

			if (this.result) {

				Toast.makeText(getActivity(), "Follow Done Successfully ... ",
						3000).show();

			} else {

				Toast.makeText(getActivity(),
						"You already followed this user !!", 3000).show();

			}
		}

	}

	// ========================================================================================================

}
