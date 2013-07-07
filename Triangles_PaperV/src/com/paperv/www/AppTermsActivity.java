package com.paperv.www;

import com.paperv.www.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.webkit.WebView;

public class AppTermsActivity extends Activity {
	
	Context myContext = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.about);
		
		WebView webview = (WebView) findViewById(R.id.webview);

		String data = "<b>Terms of Service</b>"
				+"<br class=\"pf_break\">"
				+"<br class=\"pf_break\">1. ACCEPTANCE OF TERMS"
				+"<br class=\"pf_break\">"
				+"<br class=\"pf_break\">PaperV provides the end Users (the \"User\") with a main resource, PaperV (the \"Service\") is the service provider. When the User registers a membership at the Service, they agree to these terms and are bound by these terms (the \"Terms\" or \"Terms of Service\") as long as the User is registered at the Service."
				+"<br class=\"pf_break\">"
				+"<br class=\"pf_break\">2. REGISTRATION OBLIGATIONS"
				+"<br class=\"pf_break\">"
				+"<br class=\"pf_break\">When registered at the Service, the User agrees to: (a) while registering, provide true, accurate, current, and complete information about yourself, mainly including e-mail address (the \"Registration Data\") and (b) maintain and promptly update the Registration Data to keep it true, accurate, current and complete. If the Registration Data is ever untrue, inaccurate, not current, or incomplete, PaperV has full grounds to suspend or terminate their account."
				+"<br class=\"pf_break\">"
				+"<br class=\"pf_break\">3. MEMBER ACCOUNT, PASSWORD AND SECURITY"
				+"<br class=\"pf_break\">"
				+"<br class=\"pf_break\">A password is required for the Account upon completing the Service's registration process. Only the User who created the Account on the Service (the \"User\") is responsible for maintaining the confidentiality of the password and Account, and is fully responsible for all activities that occur under the said password or Account. The User agrees to (a) immediately notify PaperV of any unauthorized use of the User's password or Account or any other breach of security, and (b) ensure that the User exits from the User account (\"Account\") at the end of each session. The Service is in no way responsible for any unauthorized use of the Account."
				+"<br class=\"pf_break\">"
				+"<br class=\"pf_break\">4. FORBIDDEN CONTENT"
				+"<br class=\"pf_break\">"
				+"<br class=\"pf_break\">The User understands that all information, data, text, software, music, sound, photographs, graphics, video, messages or other materials (the \"Content\"), whether publicly posted or privately transmitted, are the sole responsibility of the person from which such Content originated. This means that the User is entirely responsible for all Content transmitted via the Service. PaperV does not control the Content transmitted via the Service and, as such, does not guarantee the accuracy, integrity or quality of such Content."
				+"<br class=\"pf_break\">"
				+"<br class=\"pf_break\">The User agrees to NOT use the Service to:"
				+"<br class=\"pf_break\">1.upload, post, e-mail, transmit or otherwise make available any Content that is unlawful, harmful, threatening, abusive, harassing, tortuous, defamatory, vulgar, obscene, libelous, invasive of another's privacy, hateful, or racially, ethnically or otherwise objectionable;"
				+"<br class=\"pf_break\">2.impersonate any other User, unregistered visitors to the Service (\"Guests\") or Staff of the Service in any way;"
				+"<br class=\"pf_break\">3.manipulate others, including Guests, Users, the Staff via the Service;"
				+"<br class=\"pf_break\">4.upload, post, email, transmit, link to, or otherwise make available any copyrighted Content without the User/owner's consent/li&gt; "
				+"<br class=\"pf_break\">5.upload, post, email, transmit, link to, or otherwise make available any unsolicited or unauthorized advertising;"
				+"<br class=\"pf_break\">6.upload, post, email, transmit, link to, or otherwise make available any pornography, nudity, or other \"adult\" content;"
				+"<br class=\"pf_break\">7.upload, post, email, transmit, link to, or otherwise make available any material that contains software viruses or any other computer code, files or programs designed to interrupt, destroy or limit the functionality of any computer software or hardware or telecommunications equipment;"
				+"<br class=\"pf_break\">8.upload, post, email, transmit, link to, or otherwise make available any illegal copies of software (\"Warez\");"
				+"<br class=\"pf_break\">9.gambling or casino releated content;"
				+"<br class=\"pf_break\">10.\"stalk\" or otherwise harass another; or collect or store personal data about other Users;"
				+"<br class=\"pf_break\">11.insult other Users or Guests any any way, shape, or form (to \"Flame\");"
				+"<br class=\"pf_break\">12.cause (an)other User(s) or Guest(s) to react with a Flame (to \"Troll\");"
				+"<br class=\"pf_break\">13.discriminate against someone because of their race, religion, or culture."
				+"<br class=\"pf_break\">"
				+"<br class=\"pf_break\">5. CONTENT RESPONSIBILITY"
				+"<br class=\"pf_break\">"
				+"<br class=\"pf_break\">The User understands that all Content, whether publicly posted or privately transmitted, are the sole responsibility of the person from which such Content originated. This means that the User is entirely responsible for all Content transmitted via the Service. In NO WAY PaperV or the Service responsible for the Content transmitted."
				+"<br class=\"pf_break\">"
				+"<br class=\"pf_break\">6. TERMINATION OF ACCOUNTS"
				+"<br class=\"pf_break\">"
				+"<br class=\"pf_break\">PaperV has the right but not the obligation to terminate ANY account on the service if the said account has broken any part of these terms, no matter how minor. Termination of accounts is done on a case-by case basis and the specific member of the Staff of the Service determines the outcome of that particular Terms of Service violation. Each case is different, and each will be treated differently, on that member's sole discretion."
				+"<br class=\"pf_break\">"
				+"<br class=\"pf_break\">The User of the said Account on the Service is not required to be notified at any time before termination of the Account. However, in most cases, the User will be notified of the violation in hopes that the User will fix this violation. If no compliance is made, the Account will most likely be terminated. As said earlier, termination is done on a case-by-case basis, and for more severe cases, notification of the User will most likely not happen, and an action will be taken immediately. Additionally, the reason why the said account was terminated is not required to be given to the User, as the User should be aware of the violations that are occurring at the said Account."
				+"<br class=\"pf_break\">"
				+"<br class=\"pf_break\">7. MODIFICATION OF TERMS"
				+"<br class=\"pf_break\">"
				+"<br class=\"pf_break\">PaperV reserves the right to modify these terms in any way at any time, without notification to any Users or unregistered Users (the \"Guests\")."
				+"<br class=\"pf_break\">"
				+"<br class=\"pf_break\">These Terms were last modified on Tuesday, April 2, 2013. Any revisions to these Terms were not applicable before that date. However, any revisions to these terms affect all current Users, and all Users must comply to any revisions as soon as possible."
				+"<br class=\"pf_break\">"
				+"<br class=\"pf_break\">8. VIOLATIONS"
				+"<br class=\"pf_break\">"
				+"<br class=\"pf_break\">Please report any Terms of Service violations to violations@paperv.com"
				+"<br class=\"pf_break\">"
				+"<br class=\"pf_break\">9. INQUIRIES"
				+"<br class=\"pf_break\">"
				+"<br class=\"pf_break\">If there are any inquiries about these Terms, please send your inquiries to policy@paperv.com"
				+"<br class=\"pf_break\">"
				+"<br class=\"pf_break\">";
		
		
		webview.loadDataWithBaseURL("", data, "text/html", "UTF-8", "");
		
	}
	
	
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		
		finish();
		overridePendingTransition(R.anim.slide_in_left,
				R.anim.slide_out_right);
	}


}
