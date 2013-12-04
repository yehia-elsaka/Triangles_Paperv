package com.paperv.api;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.paperv.core.Constants;
import com.paperv.core.PapervActivity;
import com.paperv.lazy_adapter_utils.LazyImageLoader;
import com.paperv.models.Comment;
import com.paperv.www.R;

public class StoryAPI extends APIConnector {

	LazyImageLoader imageLoader;
	public StoryAPI(PapervActivity activityInstance, boolean showDialog,
			String url, String dialogText) {
		super(activityInstance, showDialog, url, dialogText);
	}

	@Override
	public boolean custom_doInBackground(JSONArray jsonArr) {
		JSONObject json = jsonArr.optJSONObject(0);
		boolean success = json.optBoolean("success");

		try 
		{
			JSONObject data = json.getJSONObject("data");
			cache.story_view.story_id = Integer.parseInt(data.optString("story_id"));
			cache.story_view.story_name = data.optString("title");
			cache.story_view.photo_url = activityInstance.getIntent().getExtras().getString("photo_url");

			cache.story_view.likes_number = data.optInt("total_like");
			cache.story_view.reglide_number = data.optInt("total_repost");
			cache.story_view.owner_id = String.valueOf(data.optInt("user_id"));
			cache.story_view.user_name = data.optString("user_fullname");
			cache.story_view.user_image = data.optString("user_image");
			
			JSONArray commentsArr = data.optJSONArray("comments");
			cache.story_view.comments_number = commentsArr.length();
			
			cache.story_view.comments = new ArrayList<Comment>();
			
			for(int i = 0 ; i < commentsArr.length() ; i ++)
			{
				JSONObject commentObj = commentsArr.getJSONObject(i);
				String comment_id = String.valueOf(commentObj.optInt("comment_id"));
				String text = commentObj.optString("user_comment");
				String user_id = String.valueOf(commentObj.getInt("user_id"));
				String user_name = commentObj.optString("user_fullname");
				String user_full_name = commentObj.optString("user_fullname");
				String user_image_url = commentObj.optString("user_image");
								
				Comment comment = new Comment(comment_id, text, user_id, user_name, user_full_name, user_image_url);
				cache.story_view.comments.add(comment);
			}
			
		} catch (Exception e) {
			Log.d(Constants.TAG, "ERROR => " + e.getMessage());
		}
		return success;
	}

	@Override
	public void custom_onPostExecute(boolean result) {
		
		activityInstance.setContentView(R.layout.layout_detail);

		imageLoader = new LazyImageLoader(activityInstance);
		
		ImageButton btnBack = (ImageButton)activityInstance.findViewById(R.id.btn_back);
		
		
		
		Button follow = (Button)activityInstance.findViewById(R.id.follow_user);
		
		TextView userAvatar = (TextView)activityInstance.findViewById(R.id.user_image_2);
		imageLoader.DisplayImage(cache.story_view.user_image, activityInstance, userAvatar);
		
		TextView storyImage = (TextView)activityInstance.findViewById(R.id.story_image);
		storyImage.getLayoutParams().height = cache.screenHeight/3;
		imageLoader.DisplayImage(cache.story_view.photo_url, activityInstance, storyImage);
		
		
		TextView userName = (TextView)activityInstance.findViewById(R.id.author_name);
		userName.setText(cache.story_view.user_name);
		
		TextView storyName = (TextView)activityInstance.findViewById(R.id.story_name_2);
		storyName.setText(cache.story_view.story_name);
		
		TextView numLikes = (TextView)activityInstance.findViewById(R.id.likes_number_2);
		numLikes.setText(cache.story_view.likes_number+"");
		
		TextView numReglides = (TextView)activityInstance.findViewById(R.id.reglides_number_2);
		numReglides.setText(cache.story_view.reglide_number+"");
		
		TextView numComments = (TextView)activityInstance.findViewById(R.id.comments_number_2);
		numComments.setText(cache.story_view.comments_number+"");
		
		LinearLayout commentsLayout = (LinearLayout)activityInstance.findViewById(R.id.comments_list);
		LayoutInflater inflater = LayoutInflater.from(activityInstance);
		for (int i = 0 ; i < cache.story_view.comments_number ; i ++){
			Comment comment = cache.story_view.comments.get(i);
			LinearLayout cl = (LinearLayout)inflater.inflate(R.layout.custom_comment, null);
			
			TextView comment_owner_name = (TextView)cl.findViewById(R.id.comment_owner_name);
			comment_owner_name.setText(comment.user_full_name);			
			TextView comment_owner = (TextView)cl.findViewById(R.id.comment_owner);
			imageLoader.DisplayImage(comment.user_image_url, activityInstance, comment_owner);
			
			TextView comment_text = (TextView)cl.findViewById(R.id.comment_text);
			comment_text.setText(comment.comment_text);			
			commentsLayout.addView(cl);
		}
		
		//Action Buttons
		ImageView reglide = (ImageView)activityInstance.findViewById(R.id.image_2);
		reglide.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				activityInstance.apiHandler.reglide(activityInstance, activityInstance.appInstance.getUserID(), cache.story_view.story_id);	
			}
		});
		
		ImageView like = (ImageView)activityInstance.findViewById(R.id.image_1);
		like.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				activityInstance.apiHandler.like(activityInstance, activityInstance.appInstance.getUserID(), cache.story_view.story_id);	
			}
		});
		
		
		ImageButton fbShare = (ImageButton)activityInstance.findViewById(R.id.reglide_story);
		fbShare.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				activityInstance.linkAndPostFb(cache.story_view.story_name, "www.paperv.com", "PaperV");
			}
		});
		
		ImageButton twShare = (ImageButton)activityInstance.findViewById(R.id.like_story);
		twShare.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				activityInstance.linkAndPostTw(cache.story_view.story_name, "www.paperv.com", "PaperV");
			}
		});
		
	}
	

	
}
