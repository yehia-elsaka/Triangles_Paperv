package com.paperv.core;

import java.util.ArrayList;

import android.app.Fragment;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;

import com.paperv.lazy_adapter_utils.ImageLoader;
import com.paperv.models.Story;

public abstract class PapervFragment extends Fragment implements
OnItemClickListener, OnClickListener{
	
	PapervActivity activityInstance = (PapervActivity)getActivity();
	PaperVAnimations animations = PaperVAnimations.getInstance(activityInstance);
	
	ArrayList<Story> listStories;
	
	CacheManager cache = CacheManager.getInstance();
	public ImageLoader userImageLoader; 
    public ImageLoader storyImageLoader;
    
    
	
	
	

}
