package com.paperv.tabs_fragments;


import com.paperv.www.R;
import com.paperv.www.MainActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;

public class About extends Fragment{
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		MainActivity.page_title.setText("About");
		
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
				R.layout.about, container, false);
		
//		TextView about = (TextView) theLayout.findViewById(R.id.about_text);
		
		
		String data = "<b>About Us</b>"
				+"<br class=\"pf_break\">"
				+"<br class=\"pf_break\">PaperV make your stories come to life with all your photos, video &amp; audio in a single post. It's that simple. Glide a story, Reglide story &amp; Slide a story!"
				+"<br class=\"pf_break\">"
				+"<br class=\"pf_break\">What's our story?"
				+"<br class=\"pf_break\">"
				+"<br class=\"pf_break\">We are a group of passionate technology &amp; social media innovators. By bringing together the best talent and ideas from all areas of social media to build something truly unique and usable to everyone."
				+"<br class=\"pf_break\">"
				+"<br class=\"pf_break\">We believe that everyone has a story to tell, and we are developing new and easier ways to share your stories."
				+"<br class=\"pf_break\">"
				+"<br class=\"pf_break\">We help people tell stories in a way that is more organized and easier to share. That is who we are and what we do every single day."
				+"<br class=\"pf_break\">"
				+"<br class=\"pf_break\">That is PaperV. That is our story. What is yours?"
				+"<br class=\"pf_break\">"
				+"<br class=\"pf_break\">"
				+"<br class=\"pf_break\">Made in Bahrain with Love "
				+"<br class=\"pf_break\">"
				+"<br class=\"pf_break\">";

		WebView webview = (WebView) theLayout.findViewById(R.id.webview);
		webview.loadDataWithBaseURL("", data, "text/html", "UTF-8", "");
		
		
		return theLayout;
		
	}
		

}
