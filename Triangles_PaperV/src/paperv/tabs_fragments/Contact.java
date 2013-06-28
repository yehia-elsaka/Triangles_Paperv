package paperv.tabs_fragments;

import paperv.core.MainActivity;
import paperv.core.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;

public class Contact extends Fragment{
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		MainActivity.page_title.setText("Contact Us");
		
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
		
		
		String data = "<b>Press:</b>"
				+"<br class=\"pf_break\">"
				+"<br class=\"pf_break\">14-4-2013 PaperV launches as first social network from region"
				+"<br class=\"pf_break\">http://www.ameinfo.com/paperv-launches-social-network-region-337462"
				+"<br class=\"pf_break\">"
				+"<br class=\"pf_break\">14-4-2013 First Bahraini social network is launched"
				+"<br class=\"pf_break\">http://gulf-daily-news.com/NewsDetails.aspx?storyid=351383"
				+"<br class=\"pf_break\">"
				+"<br class=\"pf_break\">"
				+"<br class=\"pf_break\">"
				+"<br class=\"pf_break\">Public Relations: pr@paperv.com"
				+"<br class=\"pf_break\">"
				+"<br class=\"pf_break\">"
				+"<br class=\"pf_break\">";

		WebView webview = (WebView) theLayout.findViewById(R.id.webview);
		webview.loadDataWithBaseURL("", data, "text/html", "UTF-8", "");
		
		
		return theLayout;
		
	}
		

}
