package com.mattwaqar.audioguide.models;

import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

@ParseClassName("Track")
public class Track extends ParseObject {

	/*
	 * A track is an audio track, GPS location, and meta-data.
	 */
	
	public static final String TAG = "Track";

	public static final String TITLE = "title";
	public static final String DESCRIPTION = "description";
	public static final String AUDIO = "audio";	
	public static final String LATITUDE = "latitude";
	public static final String LONGITUDE = "longitude";
	
	public Track() {
		
	}
	
	public String getTitle() {
		return getString(TITLE);
	}

	public void setTitle(String title) {
		put(TITLE, title);
	}
	
	public String getDescription() {
		return getString(DESCRIPTION);
	}

	public void setDescription(String description) {
		put(DESCRIPTION, description);
	}
	
	public ParseFile getAudioFile() {
		return getParseFile(AUDIO);
	}

	public void setAudioFile(ParseFile file) {
		put(AUDIO, file);
	}
	
	public LatLng getLatLng() {
		double lat = getDouble(LATITUDE);
		double lng = getDouble(LONGITUDE);
		if (lat == 0 || lng == 0) return null;
		else return new LatLng(lat, lng);
	}
	
	public void setLatLng(LatLng latLng) {
		put(LATITUDE, latLng.latitude);
		put(LONGITUDE, latLng.longitude);
	}
	
}
