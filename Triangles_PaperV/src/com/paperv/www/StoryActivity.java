package com.paperv.www;

import android.os.Bundle;

import com.paperv.core.PapervActivity;

public class StoryActivity extends PapervActivity{

	@Override
	public void onCreateUI(Bundle savedInstanceState) {
		setContentView(R.layout.activity_empty);
		int storyID = getIntent().getExtras().getInt("story_id");
		apiHandler.getStory(this, cache.user.getId(), storyID);
		
	}

}
