package com.mattwaqar.audioguide.models;

import java.util.List;

import com.activeandroid.Model;

public class Guide extends Model {

	/*
	 * A guide is a set of tracks about the same point of interest,
	 * perhaps from a different perspective, created from user associated tags
	 * or keywords. 
	 */
	
	List<String> userTags;
	List<ParseTrack> tracks;
	
}
