package com.paperv.tabs_adapters;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.paperv.lazy_adapter_utils.ImageLoader;
import com.paperv.models.PhotoItem;
import com.paperv.models.Story;
import com.paperv.www.R;

public class ImageAdapter extends PagerAdapter {
	
//	Context context;
	
	public ImageLoader imageLoader; 
	private final Activity _context;
	
	private Story story;
	private List<PhotoItem> _list;
	private static LayoutInflater _inflater = null;
	
	String current_video_url;
	
    public ImageAdapter(Activity context, Story story){
    	
    	this._context = context;
    	
    	this.story = story;
    	
    	this._list = story.getPhotos();
    	
    	_inflater = this._context.getLayoutInflater();
    	imageLoader=new ImageLoader(_context.getApplicationContext());
    }
    @Override
    public int getCount() {
      return _list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
      return view == (View)object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
    	
      View view = _inflater.inflate(R.layout.view_pager, null); 
    	
      ImageView story_image = (ImageView) view.findViewById(R.id.viewpager_image);
	  ImageView play_image = (ImageView) view.findViewById(R.id.viewpager_video);
	  
	  
	  
	  play_image.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
			
				_context.startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(current_video_url)));
			
			    }
			}); 
      
      
    
      if (_list.get(position).getType().equals("photo"))
      {
    	  story_image.setVisibility(View.VISIBLE);
    	  play_image.setVisibility(View.GONE);
    	  
    	  imageLoader.DisplayImage(_list.get(position).getItem_url(), _context, story_image);
      }
    	  
      
      if (_list.get(position).getType().equals("video"))
      {
    	  story_image.setVisibility(View.VISIBLE);
    	  play_image.setVisibility(View.VISIBLE);
    	  
    	  imageLoader.DisplayImage(story.getPhoto_url(), _context, story_image);
    	  
    	  current_video_url = _list.get(position).getItem_url();
      }
      
      view.requestFocus();
      
      ((ViewPager) container).addView(view, 0);
      return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
      ((ViewPager) container).removeView((View) object);
    }
  }
