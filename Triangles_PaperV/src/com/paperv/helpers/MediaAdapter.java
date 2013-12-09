package com.paperv.helpers;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.paperv.core.PapervActivity;
import com.paperv.lazy_adapter_utils.LazyImageLoader;
import com.paperv.models.PhotoItem;

public class MediaAdapter extends PagerAdapter {
	com.paperv.core.CacheManager cache = com.paperv.core.CacheManager.getInstance();
	PapervActivity activityInstance;
	LazyImageLoader imgLoader;
	
	public MediaAdapter(PapervActivity activityInstance) {
		this.activityInstance = activityInstance;
		imgLoader = new LazyImageLoader(activityInstance);
	}
	
	@Override
	public void setPrimaryItem(ViewGroup container, int position, Object object) {
		super.setPrimaryItem(container, position, object);
	}
	
	@Override
	public int getCount() {
		return cache.story_view.photos.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == ((TextView) object);
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		TextView image = new TextView(activityInstance);
		image.setLayoutParams(new LayoutParams(400,400));
		PhotoItem item = cache.story_view.photos.get(position);
		imgLoader.DisplayImage(item.getItem_url(), activityInstance, image);
		return image;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeView((TextView) object);
	}


}
