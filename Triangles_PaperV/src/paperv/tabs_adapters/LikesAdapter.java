package paperv.tabs_adapters;

import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import paperv.core.R;
import paperv.models.StoryItem;
import paperv.tabs_utils.Utils;

public class LikesAdapter extends ArrayAdapter<StoryItem> {

	private List<StoryItem> _list;
	private final Activity _context;
	private static LayoutInflater _inflater = null;
	private OnClickListener _listener = null;

	public LikesAdapter(Activity context, List<StoryItem> lst,
			OnClickListener listener) {
		super(context, R.layout.row_likes, lst);
		this._context = context;
		_list = lst;
		_listener = listener;

		_inflater = this._context.getLayoutInflater();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (convertView == null) {
			view = _inflater.inflate(R.layout.row_likes, null);
		}

		Utils.setFontAllView(parent);

		StoryItem vidItem = _list.get(position);
		Integer id = vidItem.get_id();

		View layer1 = view.findViewById(R.id.view_layer1);
		View layer2 = view.findViewById(R.id.view_layer2);

		TextView tvTitle = (TextView) view.findViewById(R.id.text_name);
		ImageView iv = (ImageView) view.findViewById(R.id.image);

		view.setId(id);
		tvTitle.setText(vidItem.get_title());

		Bitmap bmp = Utils.GetImageFromAssets(this._context, "images/"
				+ vidItem.get_image());
		iv.setImageBitmap(bmp);

		detailViewListener(position, view);

		layer1.setVisibility(View.VISIBLE);		
		layer2.setVisibility(View.INVISIBLE);

		return view;
	}

	public void detailViewListener(int pos, View convertView) {
		if (this._listener == null)
			return;

		ImageButton btn;
		btn = (ImageButton) convertView.findViewById(R.id.btn_love);
		btn.setTag(pos);
		btn.setOnClickListener(this._listener);

		btn = (ImageButton) convertView.findViewById(R.id.btn_like);
		btn.setTag(pos);
		btn.setOnClickListener(this._listener);

		btn = (ImageButton) convertView.findViewById(R.id.btn_reload);
		btn.setTag(pos);
		btn.setOnClickListener(this._listener);

	}

}
