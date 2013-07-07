package com.paperv.tabs_fragments;

import java.util.ArrayList;


import com.paperv.www.R;
import com.paperv.models.NotificationItem;
import com.paperv.network.DataConnector;
import com.paperv.tabs_adapters.NotificationAdapter;
import com.paperv.tabs_utils.GlobalState;
import com.paperv.www.MainActivity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

public class Notification extends Fragment{
	
	
	GlobalState globalState = GlobalState.getInstance();
	DataConnector dataConnector = DataConnector.getInstance();
	
	
	ArrayList<NotificationItem> lstNotification;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		MainActivity.page_title.setText("Notification");
		
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
				R.layout.notifications, container, false);
		

		
		// get list view
		ListView listView = (ListView) theLayout.findViewById(R.id.lst_notification);
		
		lstNotification = globalState.notification_list;
		
		NotificationAdapter notificationAdapter = new NotificationAdapter(getActivity(), lstNotification);
		
		listView.setAdapter(notificationAdapter);
		
		return theLayout;
		
	}
		

}
