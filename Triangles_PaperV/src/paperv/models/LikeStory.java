package paperv.models;

public class LikeStory {

	
	private int story_id;
	private String title;
	private String user_name;
	private String user_image;
	private String date;
	
	
	public LikeStory(){
		
	}
	
	
	public LikeStory(int story_id, String title, String user_name,
			String user_image, String date) {
		super();
		this.story_id = story_id;
		this.title = title;
		this.user_name = user_name;
		this.user_image = user_image;
		this.date = date;
	}


	public int getStory_id() {
		return story_id;
	}


	public void setStory_id(int story_id) {
		this.story_id = story_id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getUser_name() {
		return user_name;
	}


	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}


	public String getUser_image() {
		return user_image;
	}


	public void setUser_image(String user_image) {
		this.user_image = user_image;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}
	
	
	
	
	
	
}
