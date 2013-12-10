package com.paperv.core;

import android.os.Bundle;
import android.util.Log;
import android.view.View.OnClickListener;

import com.paperv.api.APIHandler;
import com.paperv.lazy_adapter_utils.LazyImageLoader;

public abstract class PapervFragment extends android.support.v4.app.Fragment implements OnClickListener{
	
	public PapervActivity activityInstance = (PapervActivity)getActivity();
	public PaperVAnimations animations = PaperVAnimations.getInstance(activityInstance);
	
	public CacheManager cache = CacheManager.getInstance();
	public APIHandler apiHandler = APIHandler.getInstance();
	
	public LazyImageLoader userImageLoader; 
    public LazyImageLoader storyImageLoader;
    
    public abstract void loadData();
    public abstract void loadMore(int pageNum);
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	activityInstance = (PapervActivity)getActivity();

    }
    
    @Override
    public void onResume() {
    	super.onResume();
    	loadData();
    }
    
	
	
	

}
