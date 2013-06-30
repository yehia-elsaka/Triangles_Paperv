package paperv.models;

public class LikeStory {

	
	private int like_id;
	private int story_id;
	private String story_title;
	private String story_image;
	private String date;
	
	
	
	public LikeStory(){
		
	}



	public LikeStory(int like_id, int story_id, String story_title,
			String story_image, String date) {
		super();
		this.like_id = like_id;
		this.story_id = story_id;
		this.story_title = story_title;
		this.story_image = story_image;
		this.date = date;
	}



	public int getLike_id() {
		return like_id;
	}



	public void setLike_id(int like_id) {
		this.like_id = like_id;
	}



	public int getStory_id() {
		return story_id;
	}



	public void setStory_id(int story_id) {
		this.story_id = story_id;
	}



	public String getStory_title() {
		return story_title;
	}



	public void setStory_title(String story_title) {
		this.story_title = story_title;
	}



	public String getStory_image() {
		return story_image;
	}



	public void setStory_image(String story_image) {
		this.story_image = story_image;
	}



	public String getDate() {
		return date;
	}



	public void setDate(String date) {
		this.date = date;
	}


	

	
	
	
}
