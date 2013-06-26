package paperv.network;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONObject;

import paperv.models.Comment;
import paperv.models.PhotoItem;
import paperv.models.Story;
import paperv.tabs_utils.GlobalState;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class DataConnector {

	// Create a local instance of cookie store
	CookieStore cookieStore = new BasicCookieStore();

	private static DataConnector instance = new DataConnector();
	private String API_URL = "http://paperv.com/webservice/";

	GlobalState globalState = GlobalState.getInstance();

	public static DataConnector getInstance() {
		return instance;
	}

	public boolean loginIn(String username, String password) {

		boolean login_done = false;

		try {

			String result = "";
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(API_URL + "login.php?login="
					+ username + "&password=" + password);

			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				result = convertStreamToString(instream);
				instream.close();
			}

			JSONArray array = new JSONArray(result);
			JSONObject object = array.getJSONObject(0);
			String status = object.optString("status");
			if (status.equals("true")) {

				List<Cookie> cookies = ((DefaultHttpClient) httpclient)
						.getCookieStore().getCookies();
				for (int i = 0; i < cookies.size(); i++) {

					cookieStore.addCookie(cookies.get(i));
				}

				// Create local HTTP context
				HttpContext localContext = new BasicHttpContext();
				// Bind custom cookie store to the local context
				localContext.setAttribute(ClientContext.COOKIE_STORE,
						cookieStore);
				response = httpclient.execute(httpget, localContext);

				entity = response.getEntity();
				if (entity != null) {
					InputStream instream = entity.getContent();
					result = convertStreamToString(instream);
					instream.close();
				}

				array = new JSONArray(result);
				object = array.getJSONObject(0);
				JSONObject data = object.getJSONObject("data");

				globalState.user.setId(data.optString("user_id"));
				globalState.user.setName(data.optString("user_name"));
				globalState.user.setEmail(data.optString("email"));

				if (data.has("user_image")) {
					String url = data.optString("user_image");
					Bitmap image = null;
					try {
						InputStream in = new java.net.URL(url).openStream();
						image = BitmapFactory.decodeStream(in);
						in.close();
					} catch (Exception e) {
						Log.e("Error", e.getMessage());
						e.printStackTrace();
					}

					globalState.user.setImage(image);

				}

				else
					globalState.user.setImage(null);

				
				// load all data
				this.getExploreFeed();
				this.getHomeFeed();
				this.getUserStories();
				this.getFollowers(globalState.user.getId());
				this.getFollowing(globalState.user.getId());

				login_done = true;

			}

			else
				login_done = false;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return login_done;
	}

	public boolean register(String fullname, String username, String password,
			String email, String image) {

		boolean register_done = false;

		try {
			String result = "";
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(API_URL + "register.php?full_name="
					+ fullname + "&user_name=" + username + "&password="
					+ password + "&email=" + email + "&image=" + image);

			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				result = convertStreamToString(instream);
				instream.close();
			}

			JSONArray array = new JSONArray(result);
			JSONObject object = array.getJSONObject(0);
			String status = object.optString("status");
			if (status.equals("true")) {

				register_done = true;
			}

			else
				register_done = false;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return register_done;
	}

	public boolean forgot(String email) {

		boolean done = false;

		try {
			String result = "";
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(API_URL
					+ "forgot_password.php?email=" + email);

			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				result = convertStreamToString(instream);
				instream.close();
			}

			JSONArray array = new JSONArray(result);
			JSONObject object = array.getJSONObject(0);
			String status = object.optString("status");
			if (status.equals("true")) {

				done = true;
			}

			else
				done = false;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return done;
	}

	public boolean getExploreFeed() {

		// Create local HTTP context
		HttpContext localContext = new BasicHttpContext();
		// Bind custom cookie store to the local context
		localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

		boolean explore_done = false;

		try {
			String result = "";
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(API_URL + "explore.php");

			HttpResponse response = httpclient.execute(httpget, localContext);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				result = convertStreamToString(instream);
				instream.close();
			}

			JSONArray array = new JSONArray(result);
			JSONObject object = array.getJSONObject(0);
			String status = object.optString("status");
			if (status.equals("true")) {

				JSONObject story_data = null;

				JSONArray data = object.getJSONArray("data");
				for (int i = 0; i < data.length(); i++) {
					story_data = data.getJSONObject(i);

					if (story_data.has("photourl")) {
						Story story = new Story();
						story.setStory_id(story_data.getInt("ITEMID"));
						story.setOwner_id(story_data.getString("user_id"));
						story.setPhoto_url(story_data.getString("photourl"));
						story.setStory_name(story_data.getString("ITEMTITLE"));
						story.setUser_name(story_data.getString("user_name"));
						story.setUser_image(story_data.getString("user_image"));
						story.setLikes_number(story_data
								.getInt("ITEMTOTALLIKE"));
						story.setReglide_number(story_data
								.getInt("ITEMTOTALREPOST"));
						story.setComments_number(story_data
								.getInt("ITEMTOTALCOMMENT"));

						globalState.explore_list.add(story);

					}
				}

				explore_done = true;
			}

			else
				explore_done = false;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return explore_done;
	}

	public boolean getHomeFeed() {

		// Create local HTTP context
		HttpContext localContext = new BasicHttpContext();
		// Bind custom cookie store to the local context
		localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

		boolean feed_done = false;

		try {
			String result = "";
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(API_URL + "feed.php");

			HttpResponse response = httpclient.execute(httpget, localContext);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				result = convertStreamToString(instream);
				instream.close();
			}

			JSONArray array = new JSONArray(result);
			JSONObject object = array.getJSONObject(0);
			String status = object.optString("status");
			if (status.equals("true")) {

				JSONObject story_data = null;

				JSONArray data = object.getJSONArray("data");
				for (int i = 0; i < data.length(); i++) {
					story_data = data.getJSONObject(i);

					if (story_data.has("photourl")) {
						Story story = new Story();
						story.setStory_id(story_data.getInt("ITEMID"));
						story.setOwner_id(story_data.getString("user_id"));
						story.setPhoto_url(story_data.getString("photourl"));
						story.setStory_name(story_data.getString("ITEMTITLE"));
						story.setUser_name(story_data.getString("user_name"));
						story.setUser_image(story_data.getString("user_image"));
						story.setLikes_number(story_data
								.getInt("ITEMTOTALLIKE"));
						story.setReglide_number(story_data
								.getInt("ITEMTOTALREPOST"));
						story.setComments_number(story_data
								.getInt("ITEMTOTALCOMMENT"));

						globalState.feed_list.add(story);

					}
				}

				feed_done = true;
			}

			else
				feed_done = false;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return feed_done;
	}

	public boolean getAllLikes() {

		// Create local HTTP context
		HttpContext localContext = new BasicHttpContext();
		// Bind custom cookie store to the local context
		localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

		boolean feed_done = false;

		try {
			String result = "";
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(API_URL + "user_liked.php");

			HttpResponse response = httpclient.execute(httpget, localContext);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				result = convertStreamToString(instream);
				instream.close();
			}

			JSONArray array = new JSONArray(result);
			JSONObject object = array.getJSONObject(0);
			String status = object.optString("status");
			if (status.equals("true")) {

				JSONObject story_data = null;

				JSONArray data = object.getJSONArray("data");
				for (int i = 0; i < data.length(); i++) {
					story_data = data.getJSONObject(i);

					// if (story_data.has("photourl")) {
					// Story story = new Story();
					// story.setStory_id(story_data.getInt("ITEMID"));
					// story.setOwner_id(story_data.getString("user_id"));
					// story.setPhoto_url(story_data.getString("photourl"));
					// story.setStory_name(story_data.getString("ITEMTITLE"));
					// story.setUser_name(story_data.getString("user_name"));
					// story.setUser_image(story_data.getString("user_image"));
					// story.setLikes_number(story_data
					// .getInt("ITEMTOTALLIKE"));
					// story.setReglide_number(story_data
					// .getInt("ITEMTOTALREPOST"));
					// story.setComments_number(story_data
					// .getInt("ITEMTOTALCOMMENT"));
					//
					// globalState.feed_list.add(story);
					//
					// }
				}

				feed_done = true;
			}

			else
				feed_done = false;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return feed_done;
	}

	public boolean getUserStories() {

		// Create local HTTP context
		HttpContext localContext = new BasicHttpContext();
		// Bind custom cookie store to the local context
		localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

		boolean feed_done = false;

		try {
			String result = "";
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(API_URL + "userstories.php");

			HttpResponse response = httpclient.execute(httpget, localContext);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				result = convertStreamToString(instream);
				instream.close();
			}

			JSONArray array = new JSONArray(result);
			JSONObject object = array.getJSONObject(0);
			String status = object.optString("status");
			if (status.equals("true")) {

				JSONObject story_data = null;

				JSONArray data = object.getJSONArray("data");
				for (int i = 0; i < data.length(); i++) {
					story_data = data.getJSONObject(i);

					if (story_data.has("photourl")) {
						Story story = new Story();
						story.setStory_id(story_data.getInt("ITEMID"));
						story.setOwner_id(story_data.getString("user_id"));
						story.setPhoto_url(story_data.getString("photourl"));
						story.setStory_name(story_data.getString("ITEMTITLE"));
						story.setUser_name(story_data.getString("user_name"));
						story.setUser_image(story_data.getString("user_image"));
						story.setLikes_number(story_data
								.getInt("ITEMTOTALLIKE"));
						story.setReglide_number(story_data
								.getInt("ITEMTOTALREPOST"));
						story.setComments_number(story_data
								.getInt("ITEMTOTALCOMMENT"));

						globalState.userStories_list.add(story);

					}
				}

				feed_done = true;
			}

			else
				feed_done = false;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return feed_done;
	}

	public boolean getStoryView(String story_id) {

		boolean getView = false;

		try {

			// Create local HTTP context
			HttpContext localContext = new BasicHttpContext();
			// Bind custom cookie store to the local context
			localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

			String result = "";
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(API_URL + "view_story.php?story_id="
					+ story_id);

			HttpResponse response = httpclient.execute(httpget, localContext);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				result = convertStreamToString(instream);
				instream.close();
			}

			JSONArray array = new JSONArray(result);
			JSONObject object = array.getJSONObject(0);
			String status = object.optString("status");
			if (status.equals("true")) {

				globalState.story_view.getPhotos().clear();

				JSONObject photo = null;

				JSONObject data = object.getJSONObject("data");
				JSONObject media = data.getJSONObject("media");
				JSONArray photos = media.getJSONArray("photo");

				for (int i = 0; i < photos.length(); i++) {
					photo = photos.getJSONObject(i);

					PhotoItem photo_item = new PhotoItem();

					if (photo.getString("type").equals("photo"))
						photo_item.setItem_url(photo.getString("destination"));
					else if (photo.getString("type").equals("video"))
						photo_item.setItem_url(photo.getString("video_url"));

					photo_item.setCaption(photo.getString("caption"));
					photo_item.setType(photo.getString("type"));

					globalState.story_view.getPhotos().add(photo_item);

				}

				getcomments(story_id);

				getView = true;

			}

			else
				getView = false;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return getView;
	}

	public boolean getcomments(String story_id) {

		boolean getView = false;

		try {

			// Create local HTTP context
			HttpContext localContext = new BasicHttpContext();
			// Bind custom cookie store to the local context
			localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

			String result = "";
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(API_URL
					+ "previous_comment.php?story_id=" + story_id);

			HttpResponse response = httpclient.execute(httpget, localContext);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				result = convertStreamToString(instream);
				instream.close();
			}

			JSONArray array = new JSONArray(result);
			JSONObject object = array.getJSONObject(0);
			String status = object.optString("status");
			if (status.equals("true")) {

				globalState.story_view.getComments().clear();

				JSONObject comment = null;

				JSONArray comments = object.getJSONArray("data");

				for (int i = 0; i < comments.length(); i++) {
					comment = comments.getJSONObject(i);

					Comment comment_item = new Comment();

					comment_item.setComment_id(comment.getString("comment_id"));
					comment_item.setCommentText(comment.getString("text"));
					comment_item.setUser_id(comment.getString("user_id"));
					comment_item.setUser_name(comment.getString("user_name"));
					comment_item.setUser_full_name(comment
							.getString("full_name"));
					comment_item.setUser_image_url(comment
							.getString("user_image"));

					globalState.story_view.getComments().add(comment_item);

				}

				if (globalState.story_view.getPhotos().size() == 0)
					getView = false;

				else
					getView = true;
			}

			else
				getView = false;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return getView;
	}

	public boolean likeStory(String story_id) {

		boolean likeDone = false;

		try {

			// Create local HTTP context
			HttpContext localContext = new BasicHttpContext();
			// Bind custom cookie store to the local context
			localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

			String result = "";
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(API_URL + "like.php?item_id="
					+ story_id);

			HttpResponse response = httpclient.execute(httpget, localContext);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				result = convertStreamToString(instream);
				instream.close();
			}

			JSONArray array = new JSONArray(result);
			JSONObject object = array.getJSONObject(0);
			String status = object.optString("status");
			if (status.equals("true")) {

				likeDone = true;
			}

			else
				likeDone = false;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return likeDone;
	}

	public boolean reglideStory(String story_id, String user_id) {

		boolean reglideDone = false;

		try {

			// Create local HTTP context
			HttpContext localContext = new BasicHttpContext();
			// Bind custom cookie store to the local context
			localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

			String result = "";
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(API_URL + "reglide.php?item_id="
					+ story_id + "&story_user_id=" + user_id);

			HttpResponse response = httpclient.execute(httpget, localContext);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				result = convertStreamToString(instream);
				instream.close();
			}

			JSONArray array = new JSONArray(result);
			JSONObject object = array.getJSONObject(0);
			String status = object.optString("status");
			String msg = object.optString("msg");
			if (status.equals("true")
					&& msg.equals("Story reglide successfully.")) {

				reglideDone = true;
			}

			else
				reglideDone = false;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return reglideDone;
	}

	public boolean glideNewStory(String title, String desc, String caption,
			String category_id, String video_url, File photo) {

		boolean glideDone = false;

		try {

			// Create local HTTP context
			HttpContext localContext = new BasicHttpContext();
			// Bind custom cookie store to the local context
			localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

			String result = "";
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(API_URL + "add_story.php?title="
					+ title + "&text=" + desc + "&categories=" + category_id
					+ "&video_url=" + video_url + "&caption=" + caption);

			if (photo != null) {
				MultipartEntity mpEntity = new MultipartEntity();
				ContentBody cbFile = new FileBody(photo, "image/*");
				mpEntity.addPart("image", cbFile);

				httppost.setEntity(mpEntity);
			}

			HttpResponse response = httpclient.execute(httppost, localContext);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				result = convertStreamToString(instream);
				instream.close();
			}

			JSONArray array = new JSONArray(result);
			JSONObject object = array.getJSONObject(0);
			String status = object.optString("status");
			if (status.equals("true")) {

				glideDone = true;
			}

			else
				glideDone = false;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return glideDone;
	}

	public boolean commentStory(String item_id, String text) {

		boolean commentDone = false;

		try {

			// Create local HTTP context
			HttpContext localContext = new BasicHttpContext();
			// Bind custom cookie store to the local context
			localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

			String result = "";
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(API_URL + "comment.php?item_id="
					+ item_id + "&text=" + text);

			HttpResponse response = httpclient.execute(httpget, localContext);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				result = convertStreamToString(instream);
				instream.close();
			}

			JSONArray array = new JSONArray(result);
			JSONObject object = array.getJSONObject(0);
			String status = object.optString("status");
			if (status.equals("true")) {

				commentDone = true;
			}

			else
				commentDone = false;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return commentDone;
	}

	
	
	
	public boolean getFollowers(String user_id) {

		// Create local HTTP context
		HttpContext localContext = new BasicHttpContext();
		// Bind custom cookie store to the local context
		localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

		boolean follow_done = false;

		try {
			String result = "";
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(API_URL + "followers.php?user_id=" + user_id);

			HttpResponse response = httpclient.execute(httpget, localContext);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				result = convertStreamToString(instream);
				instream.close();
			}

			JSONArray array = new JSONArray(result);
			JSONObject object = array.getJSONObject(0);
			String status = object.optString("status");
			if (status.equals("true")) {

				JSONArray data = object.getJSONArray("data");
				
				globalState.user_followers = data.length();

				follow_done = true;
			}

			else
				follow_done = false;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return follow_done;
	}
	
	
	
	
	public boolean getFollowing(String user_id) {

		// Create local HTTP context
		HttpContext localContext = new BasicHttpContext();
		// Bind custom cookie store to the local context
		localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

		boolean follow_done = false;

		try {
			String result = "";
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(API_URL + "following.php?user_id=" + user_id);

			HttpResponse response = httpclient.execute(httpget, localContext);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				result = convertStreamToString(instream);
				instream.close();
			}

			JSONArray array = new JSONArray(result);
			JSONObject object = array.getJSONObject(0);
			String status = object.optString("status");
			if (status.equals("true")) {

				JSONArray data = object.getJSONArray("data");
				
				globalState.user_following = data.length();

				follow_done = true;
			}

			else
				follow_done = false;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return follow_done;
	}
	
	
	
	public boolean follow(String user_id) {

		// Create local HTTP context
		HttpContext localContext = new BasicHttpContext();
		// Bind custom cookie store to the local context
		localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

		boolean follow_done = false;

		try {
			String result = "";
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(API_URL + "follow.php?user_id=" + user_id);

			HttpResponse response = httpclient.execute(httpget, localContext);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				result = convertStreamToString(instream);
				instream.close();
			}

			JSONArray array = new JSONArray(result);
			JSONObject object = array.getJSONObject(0);
			String status = object.optString("status");
			if (status.equals("true")) {

				String data = object.optString("data");
				if (data.equals("You already followed this user"))
					follow_done = false;

				else
					follow_done = true;
			}

			else
				follow_done = false;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return follow_done;
	}
	
	
	
	public String convertStreamToString(InputStream is) {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

}
