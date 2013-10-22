package com.mattwaqar.audioguide.model;

import android.location.Location;

import com.activeandroid.Model;

public class Track extends Model {

	/*
	 * A track is an audio track, location, and meta-data.
	 */
	
	Location location;
	String audioUri;
	String title;
	String author;
	
}
