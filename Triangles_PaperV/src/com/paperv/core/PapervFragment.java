package com.paperv.core;

import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.widget.AdapterView.OnItemClickListener;

import com.paperv.api.APIHandler;
import com.paperv.lazy_adapter_utils.ImageLoader;
import com.paperv.models.Story;

public abstract class PapervFragment extends android.support.v4.app.Fragment implements
OnItemClickListener, OnClickListener{
	
	public PapervActivity activityInstance = (PapervActivity)getActivity();
	public PaperVAnimations animations = PaperVAnimations.getInstance(activityInstance);
	
	public ArrayList<Story> listStories;
	public CacheManager cache = CacheManager.getInstance();
	public APIHandler apiHandler = APIHandler.getInstance();
	
	public ImageLoader userImageLoader; 
    public ImageLoader storyImageLoader;
    
    public abstract void loadData();
    public abstract void loadMore(int pageNum);
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	activityInstance = (PapervActivity)getActivity();
    	loadData();
    }
    
	
	
	

}
