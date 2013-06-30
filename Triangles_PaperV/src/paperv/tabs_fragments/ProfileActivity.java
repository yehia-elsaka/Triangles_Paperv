package paperv.tabs_fragments;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;

import paperv.core.AviaryActivity;
import paperv.core.MainActivity;
import paperv.core.R;
import paperv.lazy_adapter_utils.ImageLoader;
import paperv.models.Story;
import paperv.network.DataConnector;
import paperv.tabs_adapters.ProfileAdapter;
import paperv.tabs_utils.GlobalState;
import paperv.tabs_utils.Utils;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
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


public class ProfileActivity extends Fragment implements
		OnItemClickListener, OnClickListener {

	ListView listView;
	View footerView;
	
	ArrayList<Story> lstStories;
	ArrayList<Story> currentStories;
	View vw_master;
	View vw_detail;
	View vw_header;
	View vw_edit;
	LinearLayout comments_list;
	
	View tabs_bar;
	View comments_bar;
	
	EditText comment;
	ImageButton add_comment;
	TextView loadMore;

	// detail view
	TextView likesNumber;
	TextView commentsNumber;
	TextView reglideNumber;
	TextView  userName, stroyName;
	ImageView storyImage, userImage;
	ImageButton btnBack, btnLike, btnReglide;
	ImageView storyVideo;
	Button follow_user;

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
	
	GlobalState globalState = GlobalState.getInstance();
	DataConnector dataConnector = DataConnector.getInstance();
	
	public ImageLoader userImageLoader; 
    public ImageLoader storyImageLoader;
    
    LinearLayout next_image, previous_image, done, cancel, edit_button;
    int current_image_index;

	boolean _isBack = true;
	boolean _isEdit = false;
	
	ProfileAdapter profileAdapter;
	boolean flag_loading = false;

	File imageFile = null;
	ImageView editImage;
	EditText user_name;
	EditText user_mail;
	
	Bitmap bitmap = null;
	

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

		MainActivity.page_title.setText("Profile");
		
		userImageLoader=new ImageLoader(getActivity().getApplicationContext());
		storyImageLoader=new ImageLoader(getActivity().getApplicationContext());
		
		currentStories = new ArrayList<Story>();
		
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
				R.layout.activity_profile, container, false);
		
		LinearLayout tabs = (LinearLayout) getActivity().findViewById(R.id.tabs_bar);
		comments_bar = tabs.getChildAt(0);
		tabs_bar = tabs.getChildAt(1);
		
		comment = (EditText) comments_bar.findViewById(R.id.comment);
		comment.setText("");
		
		add_comment = (ImageButton) comments_bar.findViewById(R.id.add_comment);
		add_comment.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	
            	if (!comment.getText().toString().equals(""))
            	{
            		CommentStoryTask task = new CommentStoryTask();
        			task.execute();
            	}
            	
            }
        });

		this.vw_master = (View) theLayout.findViewById(R.id.master);
		
		// get list view
		listView = (ListView) this.vw_master.findViewById(R.id.lst_stories);

		
		this.vw_detail = (View) theLayout.findViewById(R.id.detail);
		// get detail controls
		storyImage = (ImageView) this.vw_detail.findViewById(R.id.image);
		userImage = (ImageView) this.vw_detail.findViewById(R.id.user_image_2);
		userName = (TextView) this.vw_detail.findViewById(R.id.author_name);
		stroyName = (TextView) this.vw_detail.findViewById(R.id.story_name_2);
		likesNumber = (TextView) vw_detail.findViewById(R.id.likes_number_2);
		commentsNumber = (TextView) vw_detail.findViewById(R.id.comments_number_2);
		reglideNumber = (TextView) vw_detail.findViewById(R.id.reglides_number_2);

		follow_user = (Button) vw_detail.findViewById(R.id.follow_user);
		follow_user.setVisibility(View.GONE);
		/*follow_user.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	
            	FollowTask task = new FollowTask();
    			task.execute();
    			
            }
        });*/
		
		storyVideo = (ImageView) this.vw_detail.findViewById(R.id.myvideoview);
		storyVideo.setVisibility(View.GONE);
		
		storyVideo.setOnClickListener(new OnClickListener() {

		public void onClick(View v) {
		
		    startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(current_video_url)));
		
		    }
		}); 
		
		
		comments_list = (LinearLayout) vw_detail.findViewById(R.id.comments_list);
		

		btnBack = (ImageButton) this.vw_detail.findViewById(R.id.btn_back);
		btnLike = (ImageButton) this.vw_detail.findViewById(R.id.like_story);
		btnReglide = (ImageButton) this.vw_detail.findViewById(R.id.reglide_story);

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
		
		
		
		
		next_image = (LinearLayout) this.vw_detail.findViewById(R.id.next_image);
		previous_image = (LinearLayout) this.vw_detail.findViewById(R.id.previous_image);
		
		next_image.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	getNextImage();
            }
        });
		previous_image.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	
            	getPreviousImage();
            	
            }
        });
		

		this.vw_header = (View) theLayout.findViewById(R.id.Profile_header);
		ImageView userImage = (ImageView) vw_header.findViewById(R.id.profile_user_image);
		
		TextView numberOfStories = (TextView) vw_header.findViewById(R.id.number_of_stories);
		TextView numberOfFollowers = (TextView) vw_header.findViewById(R.id.number_of_followers);
		TextView numberOfFollowing = (TextView) vw_header.findViewById(R.id.number_of_following);
		
		
		numberOfFollowers.setText(""+globalState.user_followers);
		numberOfFollowing.setText(""+globalState.user_following);
		
		if (globalState.user.getImage() != null)
		{
			userImage.setImageBitmap(globalState.user.getImage());
		}
		
		
		this.vw_edit = (View) theLayout.findViewById(R.id.edit_Profile);
		editImage = (ImageView) vw_edit.findViewById(R.id.edit_user_image);
		
		if (globalState.user.getImage() != null)
		{
			editImage.setImageBitmap(globalState.user.getImage());
		}
		
		
		user_name = (EditText) vw_edit.findViewById(R.id.user_name);
		user_name.requestFocus();
		user_name.setOnTouchListener(foucsHandler);
		
		user_mail = (EditText) vw_edit.findViewById(R.id.email_field);
		user_mail.setOnTouchListener(foucsHandler);
		

		

		
		
		cancel = (LinearLayout) this.vw_edit.findViewById(R.id.cancel);
		done = (LinearLayout) this.vw_edit.findViewById(R.id.done);
		
		cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	
            	_isEdit = false;
        		showEditView(_isEdit);
            	
            }
        });
		done.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	
            	
            	UpdateProfileTask task = new UpdateProfileTask();
    			task.execute();
            	
            	
            	
            }
        });
		
		
		final Button change_photo = (Button) theLayout.findViewById(R.id.change_photo);
		change_photo.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Perform action on click

				globalState.is_glide = false;
				globalState.is_profile = true;
				
				globalState.profile_image = null;
				
				Intent i = new Intent(getActivity(), AviaryActivity.class);
				startActivity(i);

			}
		});
		
		edit_button = (LinearLayout) this.vw_header.findViewById(R.id.edit_button);
		edit_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	
            	_isEdit = true;
        		showEditView(_isEdit);
            	
            }
        });
		
		
		this.vw_header.setVisibility(View.VISIBLE);
		this.vw_master.setVisibility(View.VISIBLE);
		this.vw_edit.setVisibility(View.GONE);
		this.vw_detail.setVisibility(View.GONE);
		
		this.tabs_bar.setVisibility(View.VISIBLE);
		this.comments_bar.setVisibility(View.GONE);

		
		// ### to be repaired
		lstStories = globalState.userStories_list;
//		getCurrentStories();
		
		numberOfStories.setText(""+lstStories.size());
		
		
		if (lstStories.size() > 0)
			currentStories.add(lstStories.get(0));
		
		profileAdapter = new ProfileAdapter(getActivity(), currentStories);
		listView.setAdapter(profileAdapter);
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
		Utils.setFontAllView((ViewGroup) vw_master);
		Utils.setFontAllView((ViewGroup) vw_detail);
		return theLayout;
	}
	
	
	
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		if (globalState.profile_image != null) {

			Uri image_uri = globalState.profile_image;
			bitmap = null;
			
	
			try {
				
				bitmap = decodeUri(image_uri);
				File dir = new File(Environment.getExternalStorageDirectory(),"paperv_uploads");
				dir.mkdir();
				String fileName = "image"+(new Date()).getTime()+".png";
				imageFile = new File(dir, fileName);
				FileOutputStream out = new FileOutputStream(imageFile);
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
				Log.d("bitmap", "File:"+ imageFile.getName()+ " Width:"+bitmap.getWidth() + " Height:"+bitmap.getHeight());

				out.flush();
				out.close();
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			editImage.setImageBitmap(bitmap);
			

		}

	}
	
	
	
	private Bitmap decodeUri(Uri selectedImage) throws FileNotFoundException {
		Bitmap bitmap;
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inSampleSize = 2;
        bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(selectedImage), null, o);
        
        if(bitmap == null)
		{
			o.inSampleSize = 4;
	        bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(selectedImage), null, o);
		}
        if(bitmap == null)
		{
			o.inSampleSize = 8;
	        bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(selectedImage), null, o);
		}
		
		// try again with more downsampling 
		if(bitmap == null)
		{
			o.inSampleSize = 16;
	        bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(selectedImage), null, o);
		}
		return bitmap;
        

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
		
		mFade = AnimationUtils.loadAnimation(getActivity(),
				R.anim.fade_in);
		
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

		if (adp != null && adp.getAdapter() instanceof ProfileAdapter) {
			ProfileAdapter newsAdp = (ProfileAdapter) adp.getAdapter();
			Story itm = (Story) newsAdp.getItem(position);

			current_image_index = 0;
			current_story_id = itm.getStory_id();
			
			globalState.story_view = itm;
			
			comments_list.removeAllViews();
			
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
				this.vw_header.setVisibility(View.VISIBLE);
				this.vw_detail.setVisibility(View.GONE);
				
				this.vw_detail.startAnimation(mSlideOutRight);
				this.vw_master.startAnimation(mSlideInLeft);
				this.vw_header.startAnimation(mSlideInTop);
				
			} else {
				this.vw_master.setVisibility(View.GONE);
				this.vw_header.setVisibility(View.GONE);
				
				this.vw_detail.setVisibility(View.VISIBLE);
				this.vw_master.startAnimation(mSlideOutLeft);
				this.vw_header.startAnimation(mSlideOutTop);
				this.vw_detail.startAnimation(mSlideInRight);
				
				this.tabs_bar.startAnimation(mSlideOutBottom);
				this.tabs_bar.setVisibility(View.GONE);
				
				this.comments_bar.setVisibility(View.VISIBLE);
				this.comments_bar.startAnimation(mFade);
			}

		} catch (Exception e) {
			
			System.out.println(e);

		}
	}
	
	
	private void showEditView(boolean isEdit) {
		try {

			if (!isEdit) {
				this.vw_master.setVisibility(View.VISIBLE);
				this.vw_header.setVisibility(View.VISIBLE);
				this.vw_edit.setVisibility(View.GONE);
				
				this.vw_edit.startAnimation(mSlideOutRight);
				this.vw_master.startAnimation(mSlideInLeft);
				this.vw_header.startAnimation(mSlideInTop);
			} else {
				this.vw_master.setVisibility(View.GONE);
				this.vw_header.setVisibility(View.GONE);
				this.vw_edit.setVisibility(View.VISIBLE);
				
				this.vw_master.startAnimation(mSlideOutLeft);
				this.vw_header.startAnimation(mSlideOutTop);
				this.vw_edit.startAnimation(mSlideInRight);
			}

		} catch (Exception e) {
			
			System.out.println(e);

		}
	}

	public void onBackPressed() {
		if (!this._isBack) {
			this._isBack = !this._isBack;
			showView(this._isBack);
			return;
		}
	}

	@Override
	public void onClick(View v) {
		
		onBackPressed();
	}
	
	
	OnTouchListener foucsHandler = new OnTouchListener() {
	    @Override
	    public boolean onTouch(View arg0, MotionEvent event) {
	        // TODO Auto-generated method stub
	        arg0.requestFocusFromTouch();
	            return false;
	    }
	};
	
	
	
	
	
	//====================================================================================
	
	
	
		private void getNextImage() {
			
			if (current_image_index+1 < globalState.story_view.getPhotos().size())
			{
				
				current_image_index++;
				
				
				
				String storyItemUrlrl = globalState.story_view.getPhotos().get(current_image_index).getItem_url();
				String type = globalState.story_view.getPhotos().get(current_image_index).getType();
				
				
				
				if (type.equals("photo"))
				{
					storyVideo.setVisibility(View.GONE);
					
					storyImage.startAnimation(mSlideInLeft);
					storyImage.setVisibility(View.VISIBLE);
					
					storyImageLoader.DisplayImage(storyItemUrlrl, getActivity(), storyImage);
				}
				else if (type.equals("video"))
				{
					
					storyImage.setVisibility(View.VISIBLE);
					storyImageLoader.DisplayImage(globalState.story_view.getPhoto_url(), getActivity(), storyImage);
					
					
					storyVideo.startAnimation(mSlideInLeft);
					storyVideo.setVisibility(View.VISIBLE);
				
					current_video_url = storyItemUrlrl;
					
					
				}
				
				
				
				
			}
			
		}
		
		private void getPreviousImage() {
			
			if (current_image_index-1 >= 0)
			{
				current_image_index--;
				
				
				String storyItemUrlrl = globalState.story_view.getPhotos().get(current_image_index).getItem_url();
				String type = globalState.story_view.getPhotos().get(current_image_index).getType();

				
				if (type.equals("photo"))
				{
					storyVideo.setVisibility(View.GONE);
					
					storyImage.startAnimation(mSlideInRight);
					storyImage.setVisibility(View.VISIBLE);
					
					storyImageLoader.DisplayImage(storyItemUrlrl, getActivity(), storyImage);
				}
				else if (type.equals("video"))
				{
					
					storyImage.setVisibility(View.VISIBLE);
					storyImageLoader.DisplayImage(globalState.story_view.getPhoto_url(), getActivity(), storyImage);
					
					
					storyVideo.startAnimation(mSlideInRight);
					storyVideo.setVisibility(View.VISIBLE);
					
					current_video_url = storyItemUrlrl;
				}
				
				
				
				
			}
			
		}

		
		
		
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
			profileAdapter.notifyDataSetChanged();
			
		}
		
		
	
	
	
	
	
	
	//====================================================================================
	
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
				dialog.setMessage("Loading Story View ...");
				dialog.show();
			}

			@Override
			protected Void doInBackground(Void... params) {

				try {
					
					
					result = dataConnector.getStoryView(""+current_story_id);
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
				if (this.result) {
					

					userName.setText(globalState.story_view.getUser_name());
					stroyName.setText(globalState.story_view.getStory_name());
					
					likesNumber.setText(""+globalState.story_view.getLikes_number());
					reglideNumber.setText(""+globalState.story_view.getReglide_number());
					commentsNumber.setText(""+globalState.story_view.getComments_number());
					
					
					String userImage_url = globalState.story_view.getUser_image();
					String storyItemUrlrl = globalState.story_view.getPhotos().get(current_image_index).getItem_url();
					String type = globalState.story_view.getPhotos().get(current_image_index).getType();
					
					
					
					userImageLoader.DisplayImage(userImage_url, getActivity(), userImage);
					
					if (type.equals("photo"))
					{
						storyVideo.setVisibility(View.GONE);
						
						storyImage.setVisibility(View.VISIBLE);
						storyImageLoader.DisplayImage(storyItemUrlrl, getActivity(), storyImage);
					}
					else if (type.equals("video"))
					{
						storyImage.setVisibility(View.VISIBLE);
						storyImageLoader.DisplayImage(globalState.story_view.getPhoto_url(), getActivity(), storyImage);
						
						
						storyVideo.setVisibility(View.VISIBLE);
						
						current_video_url = storyItemUrlrl;
						
					}
					
					
					
					for(int i = 0; i < globalState.story_view.getComments().size(); i++)
					{
						LayoutInflater _inflater = null;
						_inflater = getActivity().getLayoutInflater();
						View view = _inflater.inflate(R.layout.layout_comment, null);
						
						ImageView comment_Image = (ImageView) view.findViewById(R.id.comment_user_image);
						TextView comment_user = (TextView) view.findViewById(R.id.comment_author_name);
						TextView comment_text = (TextView) view.findViewById(R.id.Comment_text);
						
						comment_user.setText(globalState.story_view.getComments().get(i).getUser_name());
						comment_text.setText(globalState.story_view.getComments().get(i).getCommentText());
						
						ImageLoader imageLoader = new ImageLoader(getActivity().getApplicationContext());
						imageLoader.DisplayImage(globalState.story_view.getComments().get(i).getUser_image_url(), getActivity(), comment_Image);
						
						comments_list.addView(view);
						
					}
					
					showView(_isBack);
					
				} else {
					
					Toast.makeText(getActivity(), "Can't show this story !!", 3000).show();
					
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
					
					result = dataConnector.likeStory(""+current_story_id);
					//result =true;
				} catch (Exception e) {

					e.printStackTrace();
				}

				return null;

			}

			@Override
			protected void onPostExecute(Void result) {

				if (this.result) {
					
					globalState.story_view.setLikes_number(globalState.story_view.getLikes_number() + 1);
					
					likesNumber.setText("" + globalState.story_view.getLikes_number() ) ;
					
					
				} else {
					
					Toast.makeText(getActivity(), "You already like this story !!", 3000).show();
					
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
					
					result = dataConnector.commentStory(""+current_story_id, comment.getText().toString());
					//result =true;
				} catch (Exception e) {

					e.printStackTrace();
				}

				return null;

			}

			@Override
			protected void onPostExecute(Void result) {

				if (this.result) {
					
					globalState.story_view.setComments_number(globalState.story_view.getComments_number()+ 1);
					commentsNumber.setText("" + globalState.story_view.getComments_number() ) ;
					
					
					
					
					LayoutInflater _inflater = null;
					_inflater = getActivity().getLayoutInflater();
					View view = _inflater.inflate(R.layout.layout_comment, null);
					
					ImageView comment_Image = (ImageView) view.findViewById(R.id.comment_user_image);
					TextView comment_user = (TextView) view.findViewById(R.id.comment_author_name);
					TextView comment_text = (TextView) view.findViewById(R.id.Comment_text);
					
					comment_user.setText(globalState.user.getName());
					comment_text.setText(comment.getText().toString());
					
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
				dialog.setMessage("Loading Reglide ...");
				dialog.show();
			}

			@Override
			protected Void doInBackground(Void... params) {

				try {
					
					result = dataConnector.reglideStory(""+current_story_id, globalState.story_view.getOwner_id());
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
				if (this.result) {
					
					globalState.story_view.setReglide_number(globalState.story_view.getReglide_number() + 1);
					
					reglideNumber.setText("" + globalState.story_view.getReglide_number() ) ;
					
				} else {
					
					Toast.makeText(getActivity(), "You already reglide this story !!", 3000).show();
					
				}
			}

		}
		
		
		
		private class UpdateProfileTask extends AsyncTask<Void, Void, Void> {

			boolean result;
			ProgressDialog dialog = null;

			String userName = "";
			String userMail = "";
			
			@Override
			protected void onPreExecute() {
				dialog = new ProgressDialog(getActivity());
				dialog.setTitle(" PaperV ");
			
				dialog.setIcon(R.drawable.ico_dialog);
				dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				dialog.setCancelable(false);
				dialog.setMessage("Updating Profile Data ...");
				dialog.show();
			}

			@Override
			protected Void doInBackground(Void... params) {

				try {
					
					userName = user_name.getText().toString();
					userMail = user_mail.getText().toString();
					
					result = dataConnector.updateProfileData(userName, userMail);
					
					if (globalState.profile_image != null)
						result = dataConnector.updateProfilePhoto(imageFile);
						
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
				if (this.result) {
					
					
					if (!userName.equals(""))
					{
						globalState.user.setName(userName);
						user_name.setText("");
					}

					if (!userMail.equals(""))
					{
						globalState.user.setEmail(userMail);
						user_mail.setText("");
					}
					
					if (globalState.profile_image != null)
					{
						globalState.user.setImage(bitmap);
						globalState.profile_image = null;
					}
					
					_isEdit = false;
	        		showEditView(_isEdit);
					
				} else {
					
					
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
					
					result = dataConnector.follow(globalState.story_view.getOwner_id());
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
