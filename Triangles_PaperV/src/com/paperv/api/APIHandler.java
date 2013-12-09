package com.paperv.api;

import com.paperv.core.CacheManager;
import com.paperv.core.Constants;
import com.paperv.core.PapervActivity;

public class APIHandler {

	private static APIHandler instance = new APIHandler();
	CacheManager cache = CacheManager.getInstance();

	public static APIHandler getInstance() {
		return instance;
	}

	// 1 - login
	LoginAPI loginAPI;

	public void login(PapervActivity activityInstance, boolean showDialog,
			String username, String password, String dialogText,
			boolean rememberMe) {

		String url = Constants.API_LOGIN + "login=" + username + "&password="
				+ password;
		loginAPI = new LoginAPI(activityInstance, showDialog, url, dialogText);
		loginAPI.rememberMe = rememberMe;
		loginAPI.execute();

		activityInstance.appInstance.setUserName(username);
		activityInstance.appInstance.setPassword(password);

	}

	// 2 - Register
	RegisterAPI registerAPI;

	public void register(PapervActivity activityInstance, String username,
			String password, String fullname, String email, String dialogText) {
		String url = Constants.API_REGISTER + "user_name=" + username
				+ "&password=" + password + "&email=" + email + "&full_name="
				+ fullname.replaceAll(" ", "%20");
		registerAPI = new RegisterAPI(activityInstance, true, url, dialogText);
		registerAPI.execute();
	}

	// 3 - Explore Feed
	ExploreAPI exploreAPI;

	public void explore(PapervActivity activityInstance, String user_id,
			int page) {
		String url = Constants.API_EXPLORE + "user_id=" + user_id + "&page="
				+ page;
		exploreAPI = new ExploreAPI(activityInstance, true, url,
				"Loading Explore Feed");
		exploreAPI.execute();
	}

	// 4 - Home Feed
	HomeAPI homeAPI;

	public void home(PapervActivity activityInstance, String user_id, int page) {
		String url = Constants.API_HOME + "user_id=" + user_id + "&page="
				+ page;
		homeAPI = new HomeAPI(activityInstance, true, url, "Loading Home Feed");
		homeAPI.execute();
	}

	// 5 - Get Story
	StoryAPI storyAPI;

	public void getStory(PapervActivity activityInstance, String user_id,
			int storyID) {
		String url = Constants.API_GET_STORY + "user_id=" + user_id
				+ "&story_id=" + storyID;
		storyAPI = new StoryAPI(activityInstance, true, url,
				"Loading story details");
		storyAPI.execute();

	}

	// 6 - Likes Feed
	UserLikesAPI likesAPI;

	public void likes(PapervActivity activityInstance, String user_id, int page) {
		String url = Constants.API_USER_LIKES + "user_id=" + user_id + "&page="
				+ page;
		likesAPI = new UserLikesAPI(activityInstance, true, url, "Loading Likes Feed");
		likesAPI.execute();
	}
	
	// 7 - Reglide Story
	ReglideAPI reglidesAPI;
	public void reglide(PapervActivity activityInstance, String user_id, int story_id)
	{
		String url = Constants.API_REGLIDE+"user_id="+user_id+"&story_id="+story_id;
		reglidesAPI = new ReglideAPI(activityInstance, true, url, "Regliding Story ...");
		reglidesAPI.execute();
	}
	
	// 8 - Like Story
	LikeAPI likeAPI;
	public void like(PapervActivity activityInstance, String user_id, int story_id)
	{
		String url = Constants.API_LIKE+"user_id="+user_id+"&item_id="+story_id;
		likeAPI = new LikeAPI(activityInstance, true, url, "Sending Request ...");
		likeAPI.execute();
	}
	
	// 9 - Follow User
	FollowAPI followAPI;
	public void follow(PapervActivity activityInstance, String user_id, String target_id)
	{
		String url = Constants.API_FOLLOW+"user_id="+user_id+"&target_id="+target_id;
		followAPI = new FollowAPI(activityInstance, true, url, "Sending Follow Request");
		followAPI.execute();
	}
	
	// 10 - Unfollow User
		UnfollowAPI unfollowAPI;
		public void unfollow(PapervActivity activityInstance, String user_id, String target_id)
		{
			String url = Constants.API_UNFOLLOW+"user_id="+user_id+"&target_id="+target_id;
			unfollowAPI = new UnfollowAPI(activityInstance, true, url, "Sending Unfollow Request");
			unfollowAPI.execute();
		}
	
	// 11 - Add Comment
	CommentAPI commentAPI;
	public void comment(PapervActivity activityInstance, String user_id, int story_id, String comment)
	{
		String commentText = comment.replaceAll(" ", "%20");
		String url = Constants.API_ADD_COMMENT+"user_id="+user_id+"&story_id="+story_id+"&comment="+commentText;
		commentAPI = new CommentAPI(activityInstance, false, url, "");
		commentAPI.execute();
	}
	
	// 12 - Get Followings
	FollowingsAPI followingsAPI;
	public void followings(PapervActivity activityInstance, String user_id)
	{
		String url = Constants.API_USER_FOLLOWING+"user_id="+user_id;
		followingsAPI = new FollowingsAPI(activityInstance, false, url, "");
		followingsAPI.execute();
	}
	
	// 13 - User Feed
		UserFeedAPI userFeedAPI;

		public void userFeed(PapervActivity activityInstance, String user_id,
				int page) {
			String url = Constants.API_USER_STORIES + "user_id=" + user_id + "&page="
					+ page;
			userFeedAPI = new UserFeedAPI(activityInstance, true, url,
					"Loading User Feed");
			userFeedAPI.execute();
		}
		
	// 14 - Profile 
		private ProfileAPI profileAPI;
		public void getProfile(PapervActivity activityInstance, String user_id)
		{
			String url = Constants.API_USER_PROFILE+"profile_id="+user_id;
			profileAPI = new ProfileAPI(activityInstance, false, url, "Loading profile ...");
			profileAPI.execute();
		}
	
}
