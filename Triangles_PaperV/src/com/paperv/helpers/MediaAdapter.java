package com.paperv.helpers;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.paperv.core.PapervActivity;
import com.paperv.lazy_adapter_utils.LazyImageLoader;
import com.paperv.models.PhotoItem;
import com.paperv.www.R;

public class MediaAdapter extends PagerAdapter {
	com.paperv.core.CacheManager cache = com.paperv.core.CacheManager
			.getInstance();
	PapervActivity activityInstance;
	LazyImageLoader imgLoader;

	public MediaAdapter(PapervActivity activityInstance) {
		this.activityInstance = activityInstance;
		imgLoader = new LazyImageLoader(activityInstance);
	}

	

	@Override
	public int getCount() {
		Log.d("helal", "Size is " + cache.story_view.photos.size());
		//return cache.story_view.photos.size();
		return 5;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		boolean b = (view == ((View) object));
		return b;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		LayoutInflater inflater = LayoutInflater.from(activityInstance);

		View l = (View) inflater.inflate(R.layout.custom_slider_item, null);
		TextView image = (TextView) l.findViewById(R.id.image);
		PhotoItem item = cache.story_view.photos.get(position);
		//imgLoader.DisplayImage(item.getItem_url(), activityInstance, image);
		return l;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeView((View) object);
	}

}
