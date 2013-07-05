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
import com.paperv.models.NotificationItem;
import com.paperv.tabs_utils.Utils;

public class NotificationAdapter extends BaseAdapter {
    
	private List<NotificationItem> _list;
	private final Activity _context;
	private static LayoutInflater _inflater = null;
    public ImageLoader imageLoader; 
    
    
    public NotificationAdapter(Activity context, List<NotificationItem> lst) {
//		super(context, R.layout.row_home, lst);
		this._context = context;
		this._list = lst;

		_inflater = this._context.getLayoutInflater();
		imageLoader=new ImageLoader(_context.getApplicationContext());
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
			view = _inflater.inflate(R.layout.layout_notification_row, null);
		}

		Utils.setFontAllView(parent);

		NotificationItem notificationItem = _list.get(position);
		int id = notificationItem.getId();

		view.setId(id);
		
		ImageView userImage = (ImageView) view.findViewById(R.id.notification_user_image);
		TextView userName = (TextView) view.findViewById(R.id.notification_author_name);
		TextView notification_msg = (TextView) view.findViewById(R.id.notification_text);
		
		TextView date = (TextView) view.findViewById(R.id.notification_date);
		
	
		userName.setText(notificationItem.getUser_name());
		notification_msg.setText(notificationItem.getMsg());
		
		date.setText(notificationItem.getDate());
		
		String userImage_url = notificationItem.getUser_image();
		
		imageLoader.DisplayImage(userImage_url, _context, userImage);

		return view;
	}
}