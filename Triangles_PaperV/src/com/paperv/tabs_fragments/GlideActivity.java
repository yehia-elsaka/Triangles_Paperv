package com.paperv.tabs_fragments;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.paperv.core.CacheManager;
import com.paperv.lazy_adapter_utils.LazyImageLoader;
import com.paperv.network.DataConnector;
import com.paperv.www.AviaryActivity;
import com.paperv.www.MainActivity;
import com.paperv.www.R;

public class GlideActivity extends Fragment implements OnItemClickListener,
		OnClickListener {

	EditText title;
	// EditText desc;
	EditText category;
	// EditText caption;
	EditText video_link;

	ImageView image;
	ImageView user;
	Bitmap bitmap;

	int current_index = 0;
	int storyCategory = 0;

	View tabs_bar;
	View comments_bar;

	ArrayList<File> imagesArray = new ArrayList<File>();

	CacheManager globalState = CacheManager.getInstance();
	DataConnector dataConnector = DataConnector.getInstance();

	LinearLayout next_image, previous_image;
	ImageButton add_photo_btn;

	public LazyImageLoader imageLoader;
	File imageFile = null;

	// animation
	private Animation mSlideInLeft;
	private Animation mSlideOutRight;
	private Animation mSlideInRight;
	private Animation mSlideOutLeft;
	private Animation mFade;
	private Animation mSlideOutBottom;
	private Animation mSlideInBottom;
	private Animation mSlideOutTop;
	private Animation mSlideInTop;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		MainActivity.page_title.setText("Glide");

		globalState.glide_image = null;

		globalState.is_glide = false;
		globalState.is_profile = false;

		imageLoader = new LazyImageLoader(getActivity().getApplicationContext());

		LinearLayout tabs = (LinearLayout) getActivity().findViewById(
				R.id.tabs_bar);
		comments_bar = tabs.getChildAt(0);
		tabs_bar = tabs.getChildAt(1);

		this.tabs_bar.setVisibility(View.VISIBLE);
		this.comments_bar.setVisibility(View.GONE);

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
				R.layout.activity_glide, container, false);

		image = (ImageView) theLayout.findViewById(R.id.glide_image);

		user = (ImageView) theLayout.findViewById(R.id.user_image);
		if (globalState.user.getImage() != null) {
			user.setImageBitmap(globalState.user.getImage());
		}

		title = (EditText) theLayout.findViewById(R.id.editText1);
		title.requestFocus();
		title.setOnTouchListener(foucsHandler);

		/*
		 * desc = (EditText) theLayout.findViewById(R.id.editText2);
		 * desc.setOnTouchListener(foucsHandler);
		 */

		category = (EditText) theLayout.findViewById(R.id.editText6);
		category.setOnTouchListener(foucsHandler);
		category.setEnabled(false);

		// caption = (EditText) theLayout.findViewById(R.id.editText7);
		// caption.setOnTouchListener(foucsHandler);

		video_link = (EditText) theLayout.findViewById(R.id.video_url);
		video_link.setOnTouchListener(foucsHandler);

		final ImageButton add_category = (ImageButton) theLayout
				.findViewById(R.id.add_category);
		add_category.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Perform action on click

				registerForContextMenu(v);
				getActivity().openContextMenu(v);
				unregisterForContextMenu(v);

			}
		});

		add_photo_btn = (ImageButton) theLayout.findViewById(R.id.add_media);
		add_photo_btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Perform action on click

				globalState.is_glide = true;
				globalState.is_profile = false;

				globalState.glide_image = null;

				Intent i = new Intent(getActivity(), AviaryActivity.class);
				startActivity(i);
			}
		});

		next_image = (LinearLayout) theLayout.findViewById(R.id.next_image);
		previous_image = (LinearLayout) theLayout
				.findViewById(R.id.previous_image);

		next_image.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Perform action on click
				getNextImage();
			}
		});
		previous_image.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Perform action on click

				getPreviousImage();

			}
		});

		final Button glide_photo = (Button) theLayout
				.findViewById(R.id.glide_photo);
		glide_photo.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Perform action on click

				if (title.getText().toString().equals("")) {
					Toast.makeText(getActivity(),
							"Can't Glide Story ... Missing title", 3000).show();
				}
				/*
				 * else if (desc.getText().toString().equals("")) {
				 * Toast.makeText(getActivity(),
				 * "Can't Glide Story ... Missing description", 3000).show(); }
				 */
				// else if (caption.getText().toString().equals(""))
				// {
				// Toast.makeText(getActivity(),
				// "Can't Glide Story ... Missing caption", 3000).show();
				// }

				else if (storyCategory == 0) {
					Toast.makeText(getActivity(),
							"Can't Glide Story ... Missing category", 3000)
							.show();
				}

				else {
					GlideStoryTask task = new GlideStoryTask();
					task.execute();
				}

			}
		});

		video_link.setVisibility(View.GONE);
		next_image.setVisibility(View.VISIBLE);
		previous_image.setVisibility(View.VISIBLE);
		image.setVisibility(View.VISIBLE);
		add_photo_btn.setVisibility(View.VISIBLE);

		RadioGroup radioGroup = (RadioGroup) theLayout
				.findViewById(R.id.radio_group);
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// checkedId is the RadioButton selected

				switch (checkedId) {

				case R.id.radio_vedio:

					next_image.setVisibility(View.GONE);
					previous_image.setVisibility(View.GONE);
					image.setVisibility(View.GONE);
					add_photo_btn.setVisibility(View.GONE);

					video_link.setVisibility(View.VISIBLE);
					video_link.startAnimation(mSlideInTop);

					break;

				case R.id.radio_photo:

					next_image.setVisibility(View.VISIBLE);
					previous_image.setVisibility(View.VISIBLE);
					image.setVisibility(View.VISIBLE);
					add_photo_btn.setVisibility(View.VISIBLE);

					image.startAnimation(mSlideInTop);
					add_photo_btn.startAnimation(mFade);
					next_image.startAnimation(mSlideInTop);
					previous_image.startAnimation(mSlideInTop);

					video_link.setVisibility(View.GONE);

					break;
				}

			}
		});

		this.initAnimation();
		return theLayout;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

	}

	OnTouchListener foucsHandler = new OnTouchListener() {
		@Override
		public boolean onTouch(View arg0, MotionEvent event) {
			// TODO Auto-generated method stub
			arg0.requestFocusFromTouch();
			return false;
		}
	};

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		setUserVisibleHint(true);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		if (globalState.glide_image != null) {


			globalState.images.add(globalState.glide_image);
			int image_size = globalState.images.size();
			current_index = image_size - 1;
			Uri image_uri = globalState.images.get(current_index);
			bitmap = null;

			Log.d("helal", image_uri.toString());
			String ext = "";
			String fileName = "";
			
			try {

				
				/*ContentResolver cR = getActivity().getContentResolver();
				ext = cR.getType(image_uri);*/
				
				bitmap = decodeUri2(image_uri);
				File dir = new File(Environment.getExternalStorageDirectory(),
						"paperv_uploads");
				dir.mkdir();
				
//				if(ext.contains("png")) 
					fileName = "image" + (new Date()).getTime() + ".png";
				/*
				else
					fileName = "image" + (new Date()).getTime() + ".jpg";*/
					
				imageFile = new File(dir, fileName);
				FileOutputStream out = new FileOutputStream(imageFile);
				
				
//				if(ext.contains("png"))
					bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
				/*else
					bitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);*/
				
				
			
				out.flush();
				out.close();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			imagesArray.add(imageFile);

			image.setImageBitmap(bitmap);

		}

	}

	private Bitmap decodeUri(Uri uri, int factor)
	{
		bitmap = null;
		BitmapFactory.Options o = new BitmapFactory.Options();
		o.inSampleSize = factor;
		try{
			bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uri), null, o);
			if (bitmap == null)
				return decodeUri(uri, factor*2);
			
			else 
			{
				Log.d("helal", "Factor:"+factor + " Dim:"+bitmap.getWidth()+ " "+bitmap.getHeight());
				if(bitmap.getWidth() > 900 || bitmap.getHeight() > 900)				
					return decodeUri(uri, factor*2);
			}
		}
		catch(Exception e)
		{
			return decodeUri(uri, factor * 2);
		}
		
		
		Log.d("helal", "Finally -- Factor:"+factor + " Dim:"+bitmap.getWidth()+ " "+bitmap.getHeight());
		return bitmap;
	}
	
	private Bitmap decodeUri2(Uri selectedImage) throws Exception{
		Bitmap bitmap = null;
		BitmapFactory.Options o = new BitmapFactory.Options();
		int factor = 2;
		boolean satisfied = false;
		while(!satisfied){
			satisfied = true;
			o.inSampleSize = factor;
			bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver()
					.openInputStream(selectedImage), null, o);
			if (bitmap == null)
				satisfied = false;
			
			else 
			{
				Log.d("helal", "Factor:"+factor + " Dim:"+bitmap.getWidth()+ " "+bitmap.getHeight());
				if(bitmap.getWidth() > 900 || bitmap.getHeight() > 900)
				{
					factor = factor * 2;
					satisfied = false;
				}
			}
		}
		return bitmap;
	}
	
	
	

	public Bitmap bitmapFromPath(String path) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 4;
		Bitmap bitmap = BitmapFactory.decodeFile(path, options);
		return bitmap;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {

		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle("Choose Story's Category");
		menu.setHeaderIcon(R.drawable.ico_dialog);
		menu.add(0, v.getId(), 0, "Business");
		menu.add(0, v.getId(), 0, "Education");
		menu.add(0, v.getId(), 0, "Entertainment");
		menu.add(0, v.getId(), 0, "Family");
		menu.add(0, v.getId(), 0, "Health");
		menu.add(0, v.getId(), 0, "Food");
		menu.add(0, v.getId(), 0, "Shopping");
		menu.add(0, v.getId(), 0, "Society");
		menu.add(0, v.getId(), 0, "Sport");
		menu.add(0, v.getId(), 0, "Technology");
		menu.add(0, v.getId(), 0, "Travel");
		menu.add(0, v.getId(), 0, "Science");
		menu.add(0, v.getId(), 0, "Art");
		menu.add(0, v.getId(), 0, "Friends");
		menu.add(0, v.getId(), 0, "Home");
		menu.add(0, v.getId(), 0, "Fashion");
		menu.add(0, v.getId(), 0, "News");
		menu.add(0, v.getId(), 0, "Autos");
		menu.add(0, v.getId(), 0, "Music");
		menu.add(0, v.getId(), 0, "other");
		menu.add(0, v.getId(), 0, "Events");
		menu.add(0, v.getId(), 0, "Nature");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getTitle() == "Business") {
			storyCategory = 1;
			category.setEnabled(true);
			category.setText("Business");
			category.setEnabled(false);
		}

		else if (item.getTitle() == "Education") {
			storyCategory = 2;
			category.setEnabled(true);
			category.setText("Education");
			category.setEnabled(false);
		} else if (item.getTitle() == "Entertainment") {
			storyCategory = 3;
			category.setEnabled(true);
			category.setText("Entertainment");
			category.setEnabled(false);
		} else if (item.getTitle() == "Family") {
			storyCategory = 4;
			category.setEnabled(true);
			category.setText("Family");
			category.setEnabled(false);
		} else if (item.getTitle() == "Health") {
			storyCategory = 5;
			category.setEnabled(true);
			category.setText("Health");
			category.setEnabled(false);
		} else if (item.getTitle() == "Food") {
			storyCategory = 6;
			category.setEnabled(true);
			category.setText("Food");
			category.setEnabled(false);
		} else if (item.getTitle() == "Shopping") {
			storyCategory = 7;
			category.setEnabled(true);
			category.setText("Shopping");
			category.setEnabled(false);
		} else if (item.getTitle() == "Society") {
			storyCategory = 8;
			category.setEnabled(true);
			category.setText("Society");
			category.setEnabled(false);
		} else if (item.getTitle() == "Sport") {
			storyCategory = 9;
			category.setEnabled(true);
			category.setText("Sport");
			category.setEnabled(false);
		} else if (item.getTitle() == "Technology") {
			storyCategory = 10;
			category.setEnabled(true);
			category.setText("Technology");
			category.setEnabled(false);
		} else if (item.getTitle() == "Travel") {
			storyCategory = 11;
			category.setEnabled(true);
			category.setText("Travel");
			category.setEnabled(false);
		} else if (item.getTitle() == "Science") {
			storyCategory = 12;
			category.setEnabled(true);
			category.setText("Science");
			category.setEnabled(false);
		} else if (item.getTitle() == "Art") {
			storyCategory = 13;
			category.setEnabled(true);
			category.setText("Art");
			category.setEnabled(false);
		} else if (item.getTitle() == "Friends") {
			storyCategory = 14;
			category.setEnabled(true);
			category.setText("Friends");
			category.setEnabled(false);
		} else if (item.getTitle() == "Home") {
			storyCategory = 15;
			category.setEnabled(true);
			category.setText("Home");
			category.setEnabled(false);
		} else if (item.getTitle() == "Fashion") {
			storyCategory = 16;
			category.setEnabled(true);
			category.setText("Fashion");
			category.setEnabled(false);
		} else if (item.getTitle() == "News") {
			storyCategory = 17;
			category.setEnabled(true);
			category.setText("News");
			category.setEnabled(false);
		} else if (item.getTitle() == "Autos") {
			storyCategory = 18;
			category.setEnabled(true);
			category.setText("Autos");
			category.setEnabled(false);
		} else if (item.getTitle() == "Music") {
			storyCategory = 19;
			category.setEnabled(true);
			category.setText("Music");
			category.setEnabled(false);
		} else if (item.getTitle() == "other") {
			storyCategory = 20;
			category.setEnabled(true);
			category.setText("other");
			category.setEnabled(false);
		} else if (item.getTitle() == "Events") {
			storyCategory = 21;
			category.setEnabled(true);
			category.setText("Events");
			category.setEnabled(false);
		} else if (item.getTitle() == "Nature") {
			storyCategory = 22;
			category.setEnabled(true);
			category.setText("Nature");
			category.setEnabled(false);
		}

		return true;
	}

	// ========================================================================

	private void getNextImage() {

		if (current_index + 1 < CacheManager.getInstance().images.size()) {
			current_index++;

			Uri image_uri = globalState.images.get(current_index);
			bitmap = null;
			try {
				bitmap = decodeUri2(image_uri);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			image.startAnimation(mSlideInLeft);
			image.setImageBitmap(bitmap);

		}

	}

	private void getPreviousImage() {

		if (current_index - 1 >= 0) {
			current_index--;

			Uri image_uri = globalState.images.get(current_index);
			bitmap = null;
			try {
				bitmap = decodeUri2(image_uri);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			image.startAnimation(mSlideInRight);
			image.setImageBitmap(bitmap);
		}

	}

	// private static Bitmap codec(Bitmap src, Bitmap.CompressFormat format, int
	// quality) {
	// ByteArrayOutputStream os = new ByteArrayOutputStream();
	// src.compress(format, quality, os);
	//
	// byte[] array = os.toByteArray();
	// return BitmapFactory.decodeByteArray(array, 0, array.length);
	// }

	private void initAnimation() {
		// animation
		mSlideInLeft = AnimationUtils.loadAnimation(getActivity(),
				R.anim.push_left_in);
		mSlideOutRight = AnimationUtils.loadAnimation(getActivity(),
				R.anim.push_right_out);
		mSlideInRight = AnimationUtils.loadAnimation(getActivity(),
				R.anim.push_right_in);
		mSlideOutLeft = AnimationUtils.loadAnimation(getActivity(),
				R.anim.push_left_out);

		mFade = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);

		mSlideOutBottom = AnimationUtils.loadAnimation(getActivity(),
				R.anim.slide_out_bottom);

		mSlideInBottom = AnimationUtils.loadAnimation(getActivity(),
				R.anim.slide_in_bottom);

		mSlideOutTop = AnimationUtils.loadAnimation(getActivity(),
				R.anim.slide_out_top);

		mSlideInTop = AnimationUtils.loadAnimation(getActivity(),
				R.anim.slide_in_top);
	}

	// =============================================================================================

	private class GlideStoryTask extends AsyncTask<Void, Void, Boolean> {

		//boolean result;
		ProgressDialog dialog = null;

		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(getActivity());
			dialog.setTitle(" PaperV ");

			dialog.setIcon(R.drawable.ico_dialog);
			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			dialog.setCancelable(false);
			dialog.setMessage("Gliding the story ...");
			dialog.show();
		}

		@Override
		protected Boolean doInBackground(Void... params) {

			try {

				String storyTitle = title.getEditableText().toString()
						.replaceAll(" ", "%20");
				// String storyDescription =
				// desc.getEditableText().toString().replaceAll(" ", "%20");
				// String storyCaption =
				// caption.getEditableText().toString().replaceAll(" ", "%20");

				String category = storyCategory + "";
				String video_url = video_link.getEditableText().toString()
						.replaceAll(" ", "%20");

				if (storyTitle.length() > 0) {
					if (video_url.length() > 0 || imagesArray.size() > 0) {
						
						String video_url_base64 = "";
						
						if (video_url.length() > 0)
						{
							video_url_base64 = Base64.encodeToString(
									video_url.getBytes(), Base64.DEFAULT);
							video_url_base64 = video_url_base64
									.replaceAll("\n", "");
						}
						
						return dataConnector.glideNewStory(storyTitle, "",
								"", category, video_url_base64, imagesArray);
						
					}

					else
						return false;
				} else
					return false;

				// result =true;
			} catch (Exception e) {
				
				e.printStackTrace();
				return false;
			}


		}

		@Override
		protected void onPostExecute(Boolean result) {

			try {
				dialog.dismiss();
			} catch (Exception e) {

			}
			if (result) {

				Toast.makeText(getActivity(), "Gliding Done ...", Toast.LENGTH_LONG).show();

				title.setText("");
				// desc.setText("");
				// caption.setText("");

				video_link.setText("");

				category.setText("Category");
				
				
				globalState.images.clear();
				imagesArray.clear();

			} else {
				Toast.makeText(getActivity(),
						"Can't Glide Story ... Missing Media", Toast.LENGTH_LONG).show();
			}
		}

	}

}
