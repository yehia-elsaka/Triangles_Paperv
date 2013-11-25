package com.paperv.helpers;

import android.content.Context;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.View;
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

public class FeedHelper extends BaseAdapter {

	Context mContext;
	LazyImageLoader loader;

	public FeedHelper(Context mContext) {
		this.mContext = mContext;
		loader = new LazyImageLoader(mContext);

	}

	CacheManager cache = CacheManager.getInstance();

	@Override
	public int getCount() {
		return cache.feed_list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return cache.feed_list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {

		Story story = cache.feed_list.get(arg0);
		LayoutInflater inflater = LayoutInflater.from(mContext);

		// main layout
		RelativeLayout l = (RelativeLayout) inflater.inflate(
				R.layout.custom_story, null);
		Point p = ((PapervActivity) mContext).getFeedDimensions();

		l.setLayoutParams(new AbsListView.LayoutParams(p.x, p.y));

		// update with story details

		TextView storyTitle = (TextView) l.findViewById(R.id.story_title);
		storyTitle.setText(story.story_name);

		TextView textView = (TextView) l.findViewById(R.id.story_photo);

		loader.DisplayImage(story.photo_url, (PapervActivity) mContext,
				textView);

		TextView storyOwner = (TextView) l.findViewById(R.id.story_owner);
		storyOwner.setText(story.user_name);

		TextView numLikes = (TextView) l.findViewById(R.id.num_likes);
		numLikes.setText(story.likes_number + "");

		TextView numReglides = (TextView) l.findViewById(R.id.num_reglides);
		numReglides.setText(story.reglide_number + "");

		TextView numComments = (TextView) l.findViewById(R.id.num_comments);
		numComments.setText(story.comments_number + "");

		LinearLayout ownerPhotoLayout = (LinearLayout) l
				.findViewById(R.id.user_img);

		ownerPhotoLayout.setLayoutParams(new RelativeLayout.LayoutParams(100,
				100));
		TextView ownerPhoto = (TextView) l.findViewById(R.id.owner_photo);
		// ownerPhoto.setLayoutParams(new LayoutParams(cache.screenWidth / 10,
		// cache.screenWidth / 10));
		loader.DisplayImage(story.user_image, (PapervActivity) mContext,
				ownerPhoto);

		return l;
	}

}
