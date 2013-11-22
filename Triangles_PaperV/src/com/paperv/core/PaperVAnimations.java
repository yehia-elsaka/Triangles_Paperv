package com.paperv.core;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.paperv.www.R;

public class PaperVAnimations {
	private static PapervActivity activityInstance;
	private static PaperVAnimations instance = new PaperVAnimations();
	public static PaperVAnimations getInstance(PapervActivity activity){
		activityInstance = activity;
		return instance;
	}
	
	public Animation mSlideInLeft;
	public Animation mSlideOutRight;
	public Animation mSlideInRight;
	public Animation mSlideOutLeft;
	public Animation mFade;
	public Animation mSlideOutBottom;
	public Animation mSlideInBottom;
	public Animation mSlideOutTop;
	public Animation mSlideInTop;
	

	
	public void initAnimation() {
		// animation
		mSlideInLeft = AnimationUtils.loadAnimation(activityInstance,
				R.anim.push_left_in);
		mSlideOutRight = AnimationUtils.loadAnimation(activityInstance,
				R.anim.push_right_out);
		mSlideInRight = AnimationUtils.loadAnimation(activityInstance,
				R.anim.push_right_in);
		mSlideOutLeft = AnimationUtils.loadAnimation(activityInstance,
				R.anim.push_left_out);
		
		mFade = AnimationUtils.loadAnimation(activityInstance,
				R.anim.fade_in);
		
		mSlideOutBottom = AnimationUtils.loadAnimation(activityInstance,
				R.anim.slide_out_bottom);
		
		mSlideInBottom = AnimationUtils.loadAnimation(activityInstance,
				R.anim.slide_in_bottom);
		
		mSlideOutTop = AnimationUtils.loadAnimation(activityInstance,
				R.anim.slide_out_top);
		
		mSlideInTop = AnimationUtils.loadAnimation(activityInstance,
				R.anim.slide_in_top);
	}

}
