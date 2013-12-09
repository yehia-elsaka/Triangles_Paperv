package com.paperv.helpers;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.paperv.core.CacheManager;
import com.paperv.core.PapervActivity;
import com.paperv.lazy_adapter_utils.LazyImageLoader;
import com.paperv.models.Story;
import com.paperv.www.R;
import com.paperv.www.StoryActivity;

public class FeedHelper extends BaseAdapter {

	Context mContext;
	LazyImageLoader loader;
	ArrayList<Story> storyList;

	public FeedHelper(Context mContext, ArrayList<Story> storyList) {
		this.storyList = storyList;
		this.mContext = mContext;
		loader = new LazyImageLoader(mContext);
		

	}

	public void releod(ArrayList<Story> storyList){
		this.storyList = storyList;
		notifyDataSetChanged();
		
	}
	CacheManager cache = CacheManager.getInstance();

	public void clear(){
		storyList = new ArrayList<Story>();
		notifyDataSetChanged();
	}
	
	
	@Override
	public int getCount() {
		return storyList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return storyList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {

		final Story story = storyList.get(arg0);
		LayoutInflater inflater = LayoutInflater.from(mContext);
		return loadStory(story, inflater);
		
		
	}
	
	
	public View loadStory(final Story story, LayoutInflater inflater){
		LinearLayout l = (LinearLayout)inflater.inflate(R.layout.row_home, null);
		Point p = ((PapervActivity) mContext).getFeedDimensions();
		l.setLayoutParams(new AbsListView.LayoutParams(p.x, AbsListView.LayoutParams.WRAP_CONTENT));
		
		TextView storyTitle = (TextView) l.findViewById(R.id.story_name);
		storyTitle.setText(story.story_name);
		
		TextView userName = (TextView) l.findViewById(R.id.user);
		userName.setText(story.user_name);
		
		TextView numLikes = (TextView) l.findViewById(R.id.likes_number);
		numLikes.setText(story.likes_number + "");

		TextView numReglides = (TextView) l.findViewById(R.id.reglide_number);
		numReglides.setText(story.reglide_number + "");

		TextView numComments = (TextView) l.findViewById(R.id.comments_number);
		numComments.setText(story.comments_number + "");
		
		TextView storyImage = (TextView) l.findViewById(R.id.image);
		loader.DisplayImage(story.photo_url, (PapervActivity) mContext,storyImage);
		storyImage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent storyDetails = new Intent(mContext, StoryActivity.class);
				storyDetails.putExtra("story_id", story.story_id);
				storyDetails.putExtra("photo_url", story.photo_url);
				mContext.startActivity(storyDetails);
			}
		});
		
		TextView ownerPhoto = (TextView) l.findViewById(R.id.user_image);
		loader.DisplayImage(story.user_image, (PapervActivity) mContext,ownerPhoto);
		
		return l;

	}
	
	
	

}
