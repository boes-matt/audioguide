package com.mattwaqar.audioguide.models;

import java.util.List;

public class Guide {

	/*
	 * A guide is a set of tracks about the same point of interest,
	 * perhaps from a different perspective, created from user associated tags
	 * or keywords. 
	 */
	
	List<String> userTags;
	List<ParseTrack> tracks;
	
}
