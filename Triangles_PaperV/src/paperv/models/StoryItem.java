package paperv.models;

public class StoryItem {
	private int _id;
	private String _userName;
	private String _userImage;
	private String _title;
	private String _image;
	private String _desc;
	private Boolean _selected;

	public StoryItem() {
	};

	public StoryItem(int id, String userName, String userImage, String title, String image, String desc,Boolean selected) {
		this._id = id;
		this._userName = userName;
		this._userImage = userImage;
		this._title = title;
		this._image = image;
		this._desc = desc;
		this._selected = selected;
	};

	public String get_title() {
		return _title;
	}

	public void set_title(String _title) {
		this._title = _title;
	}

	public int get_id() {
		return _id;
	}

	public String get_userName() {
		return _userName;
	}

	public String get_userImage() {
		return _userImage;
	}

	public String get_image() {
		return _image;
	}

	public String get_desc() {
		return _desc;
	}
	
	public Boolean get_selected() {
		return _selected;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public void set_userName(String _userName) {
		this._userName = _userName;
	}

	public void set_userImage(String _userImage) {
		this._userImage = _userImage;
	}

	public void set_image(String _image) {
		this._image = _image;
	}

	public void set_desc(String _desc) {
		this._desc = _desc;
	}
	
	public void set_selected(Boolean _selected) {
		this._selected = _selected;
	}

}
