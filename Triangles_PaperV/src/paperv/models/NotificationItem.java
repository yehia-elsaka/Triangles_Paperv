package paperv.models;

public class NotificationItem {
	
	private int id;
	private String user_image;
	private String user_name;
	private String msg;
	private String date;
	
	public NotificationItem()
	{
		
	}

	public NotificationItem(int id, String user_image, String user_name,
			String msg, String date) {
		super();
		this.id = id;
		this.user_image = user_image;
		this.user_name = user_name;
		this.msg = msg;
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUser_image() {
		return user_image;
	}

	public void setUser_image(String user_image) {
		this.user_image = user_image;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	
	
	

}
