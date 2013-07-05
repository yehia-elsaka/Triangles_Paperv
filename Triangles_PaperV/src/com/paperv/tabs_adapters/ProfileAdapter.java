package com.paperv.tabs_adapters;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.paperv.androidapp.R;
import com.paperv.lazy_adapter_utils.ImageLoader;
import com.paperv.models.Story;
import com.paperv.tabs_utils.Utils;



public class ProfileAdapter extends BaseAdapter {
    
	private List<Story> _list;
	private final Activity _context;
	private static LayoutInflater _inflater = null;
    public ImageLoader userImageLoader; 
    public ImageLoader storyImageLoader;
    
    
    public ProfileAdapter(Activity context, List<Story> lst) {
//		super(context, R.layout.row_home, lst);
		this._context = context;
		this._list = lst;

		_inflater = this._context.getLayoutInflater();
		userImageLoader=new ImageLoader(_context.getApplicationContext());
		storyImageLoader=new ImageLoader(_context.getApplicationContext());
	}
    
    

    public int getCount() {
        return _list.size();
    }

    public Object getItem(int position) {
        return _list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }
    
   
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (convertView == null) {
			view = _inflater.inflate(R.layout.row_home, null);
		}

		Utils.setFontAllView(parent);

		Story storyItem = _list.get(position);
		int id = storyItem.getStory_id();

		view.setId(id);
		
		TextView userName = (TextView) view.findViewById(R.id.user);
		ImageView userImage = (ImageView) view.findViewById(R.id.user_image);
		TextView storyName = (TextView) view.findViewById(R.id.story_name);
		ImageView storyImage = (ImageView) view.findViewById(R.id.image);
		
		TextView likesNumber = (TextView) view.findViewById(R.id.likes_number);
		TextView commentsNumber = (TextView) view.findViewById(R.id.comments_number);
		TextView reglideNumber = (TextView) view.findViewById(R.id.reglide_number);
		

		view.setId(id);
		
		
		userName.setText(storyItem.getUser_name());
		storyName.setText(storyItem.getStory_name());
		
		likesNumber.setText(""+storyItem.getLikes_number());
		commentsNumber.setText(""+storyItem.getComments_number());
		reglideNumber.setText(""+storyItem.getReglide_number());
		
		
		
		String userImage_url = storyItem.getUser_image();
		String storyImage_url = storyItem.getPhoto_url();
		
		userImageLoader.DisplayImage(userImage_url, _context, userImage);
		storyImageLoader.DisplayImage(storyImage_url, _context, storyImage);

		return view;
	}
}