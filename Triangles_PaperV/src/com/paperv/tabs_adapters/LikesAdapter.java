package com.paperv.tabs_adapters;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.paperv.lazy_adapter_utils.LazyImageLoader;
import com.paperv.models.LikeStory;
import com.paperv.tabs_utils.Utils;
import com.paperv.www.R;

public class LikesAdapter extends BaseAdapter {

	private List<LikeStory> _list;
	private final Activity _context;
	private static LayoutInflater _inflater = null;
	
	public LazyImageLoader imageLoader; 

	public LikesAdapter(Activity context, List<LikeStory> lst) {
//		super(context, R.layout.row_likes, lst);
		this._context = context;
		_list = lst;

		_inflater = this._context.getLayoutInflater();
		
		imageLoader=new LazyImageLoader(_context.getApplicationContext());
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (convertView == null) {
			view = _inflater.inflate(R.layout.row_likes, null);
		}

		Utils.setFontAllView(parent);

		LikeStory item = _list.get(position);
		Integer id = item.getStory_id();
		view.setId(id);
		
		
		ImageView storyImage = (ImageView) view.findViewById(R.id.like_image);
		TextView storyTitle = (TextView) view.findViewById(R.id.like_story_name);
		
		TextView date = (TextView) view.findViewById(R.id.like_date);
		
		
	
		storyTitle.setText(item.getStory_title());
		date.setText(item.getDate());
		
		
		
		String image_url = item.getStory_image();
		
	//	imageLoader.DisplayImage(image_url, _context, storyImage);
		


		return view;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return _list.size();
	}

	@Override
	 public Object getItem(int position) {
        return _list.get(position);
    }

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	
}
