package com.paperv.models;

import java.util.ArrayList;

public class StoryList {

	public static ArrayList<StoryItem> getVideoList() {
		ArrayList<StoryItem> resultList = new ArrayList<StoryItem>();

		StoryItem itm;

		itm = new StoryItem(
				1,
				"Yehia Elsaka ..",
				"Brave",
				"Brave",
				"brave.jpg",
				"Determined to make her own path in life, Princess Merida defies a custom that brings chaos to her kingdom. Granted one wish, Merida must rely on her bravery and her archery skills to undo a beastly curse.",
				true);
		resultList.add(itm);

		itm = new StoryItem(
				2,
				"Author Name ..",
				"Ice Age",
				"Ice Age",
				"ice_age.jpg",
				"Manny, Diego, and Sid embark upon another adventure after their continent is set adrift. Using an iceberg as a ship, they encounter sea creatures and battle pirates as they explore a new world.",
				false);
		resultList.add(itm);

		itm = new StoryItem(
				3,
				"Author Name ..",
				"The Incredibles",
				"The Incredibles",
				"incredibles.jpg",
				"A family of undercover superheroes, while trying to live the quiet suburban life, are forced into action to save the world.",
				true);
		resultList.add(itm);

		itm = new StoryItem(
				4,
				"Author Name ..",
				"Finding Nemo",
				"Finding Nemo",
				"nemo.jpg",
				"After his son is captured in the Great Barrier Reef and taken to Sydney, a timid clownfish sets out on a journey to bring him home.",
				false);
		resultList.add(itm);

		itm = new StoryItem(
				5,
				"Author Name ..",
				"UP",
				"UP",
				"up.jpg",
				"By tying thousands of balloons to his home, 78-year-old Carl sets out to fulfill his lifelong dream to see the wilds of South America. Russell, a wilderness explorer 70 years younger, inadvertently becomes a stowaway.",
				false);
		resultList.add(itm);

		
		return resultList;
	};

}
