package com.paperv.www;

import com.paperv.www.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.webkit.WebView;

public class AppPrivacyActivity extends Activity {

	Context myContext = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.about);

		WebView webview = (WebView) findViewById(R.id.webview);

		String data = "<b>Privacy Policy</b>"
				+"<br class=\"pf_break\">"
				+"<br class=\"pf_break\">This Privacy Policy governs the manner in which PaperV collects, uses, maintains and discloses information collected from users (each, a \"User\") of the http://www.paperv.com website (\"Site\"). This privacy policy applies to the Site and all products and services offered by PaperV."
				+"<br class=\"pf_break\">"
				+"<br class=\"pf_break\">Personal identification information"
				+"<br class=\"pf_break\">"
				+"<br class=\"pf_break\">We may collect personal identification information from Users in a variety of ways, including, but not limited to, when Users visit our site, register on the site, and in connection with other activities, services, features or resources we make available on our Site. Users may be asked for, as appropriate, name, email address. Users may, however, visit our Site anonymously. We will collect personal identification information from Users only if they voluntarily submit such information to us. Users can always refuse to supply personally identification information, except that it may prevent them from engaging in certain Site related activities."
				+"<br class=\"pf_break\">"
				+"<br class=\"pf_break\">Non-personal identification information"
				+"<br class=\"pf_break\">"
				+"<br class=\"pf_break\">We may collect non-personal identification information about Users whenever they interact with our Site. Non-personal identification information may include the browser name, the type of computer and technical information about Users means of connection to our Site, such as the operating system and the Internet service providers utilized and other similar information."
				+"<br class=\"pf_break\">"
				+"<br class=\"pf_break\">Web browser cookies"
				+"<br class=\"pf_break\">"
				+"<br class=\"pf_break\">Our Site may use \"cookies\" to enhance User experience. User's web browser places cookies on their hard drive for record-keeping purposes and sometimes to track information about them. User may choose to set their web browser to refuse cookies, or to alert you when cookies are being sent. If they do so, note that some parts of the Site may not function properly."
				+"<br class=\"pf_break\">"
				+"<br class=\"pf_break\">How we use collected information"
				+"<br class=\"pf_break\">"
				+"<br class=\"pf_break\">PaperV may collect and use Users personal information for the following purposes:"
				+"<br class=\"pf_break\">"
				+"<br class=\"pf_break\">- To personalize user experience"
				+"<br class=\"pf_break\">	We may use information in the aggregate to understand how our Users as a group use the services and resources provided on our Site."
				+"<br class=\"pf_break\">- To send periodic emails"
				+"<br class=\"pf_break\">If User decides to opt-in to our mailing list, they will receive emails that may include company news, updates, related product or service information, etc. If at any time the User would like to unsubscribe from receiving future emails, they may do so by contacting us via our Site."
				+"<br class=\"pf_break\">"
				+"<br class=\"pf_break\">How we protect your information"
				+"<br class=\"pf_break\">"
				+"<br class=\"pf_break\">We adopt appropriate data collection, storage and processing practices and security measures to protect against unauthorized access, alteration, disclosure or destruction of your personal information, username, password, transaction information and data stored on our Site."
				+"<br class=\"pf_break\">"
				+"<br class=\"pf_break\">Sharing your personal information"
				+"<br class=\"pf_break\">"
				+"<br class=\"pf_break\">We do not sell, trade, or rent Users personal identification information to others. We may share generic aggregated demographic information not linked to any personal identification information regarding visitors and users with our business partners, trusted affiliates and advertisers for the purposes outlined above."
				+"<br class=\"pf_break\">"
				+"<br class=\"pf_break\">Advertising"
				+"<br class=\"pf_break\">"
				+"<br class=\"pf_break\">Ads appearing on our site may be delivered to Users by advertising partners, who may set cookies. These cookies allow the ad server to recognize your computer each time they send you an online advertisement to compile non personal identification information about you or others who use your computer. This information allows ad networks to, among other things, deliver targeted advertisements that they believe will be of most interest to you. This privacy policy does not cover the use of cookies by any advertisers."
				+"<br class=\"pf_break\">"
				+"<br class=\"pf_break\">Changes to this privacy policy"
				+"<br class=\"pf_break\">"
				+"<br class=\"pf_break\">PaperV has the discretion to update this privacy policy at any time. When we do, we will revise the updated date at the bottom of this page and send you an email. We encourage Users to frequently check this page for any changes to stay informed about how we are helping to protect the personal information we collect. You acknowledge and agree that it is your responsibility to review this privacy policy periodically and become aware of modifications."
				+"<br class=\"pf_break\">"
				+"<br class=\"pf_break\">Your acceptance of these terms"
				+"<br class=\"pf_break\">"
				+"<br class=\"pf_break\">By using this Site, you signify your acceptance of this policy and terms of service. If you do not agree to this policy, please do not use our Site. Your continued use of the Site following the posting of changes to this policy will be deemed your acceptance of those changes."
				+"<br class=\"pf_break\">"
				+"<br class=\"pf_break\">Contacting us"
				+"<br class=\"pf_break\">"
				+"<br class=\"pf_break\">If you have any questions about this Privacy Policy, the practices of this site, or your dealings with this site, please contact us at:"
				+"<br class=\"pf_break\">PaperV"
				+"<br class=\"pf_break\">http://www.paperv.com"
				+"<br class=\"pf_break\">privacy@paperv.com"
				+"<br class=\"pf_break\">"
				+"<br class=\"pf_break\">This document was last updated on April 02, 2013"
				+"<br class=\"pf_break\">"
				+"<br class=\"pf_break\">";

		webview.loadDataWithBaseURL("", data, "text/html", "UTF-8", "");

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();

		finish();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}

}
