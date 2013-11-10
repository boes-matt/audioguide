package com.mattwaqar.audioguide.models;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.parse.FindCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

@ParseClassName("Track")
public class Track extends ParseObject {

	/*
	 * A track is an audio track, GPS location, and meta-data.
	 */
	public static final String CLASS_NAME = "Track";
	public static final String TITLE_KEY = "title";
	public static final String DESCRIPTION_KEY = "description";
	public static final String AUTHOR_KEY = "author";
	public static final String AUDIO_RESOURCE_KEY = "audioResource";
	public static final String AUDIO_PATH_KEY = "audioPath";
	public static final String LATITUDE_KEY = "latitude";
	public static final String LONGITUDE_KEY = "longitude";
	
	private ParseObject _parseObject;	
	
	public Track(ParseObject object) {
		setParseObject(object);
	}
	
	public static final String TAG = "Track";
		_parseObject = new ParseObject(CLASS_NAME);
		setTitle(title);
		setDescription(description);
		setAuthor(author);
		setAudioResource(audioResource);
		setLocation(latLng);
	}

	public static final String TITLE = "title";
		_parseObject = new ParseObject(CLASS_NAME);
		setTitle(title);
	public static final String DESCRIPTION = "description";
	public static final String AUDIO = "audio";	
		setAudioPath(audioPath);
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

	public String getAuthor() {
		return (String) _parseObject.get(AUTHOR_KEY);
	}

	public void setAuthor(String author) {
		_parseObject.put(AUTHOR_KEY, author);
	}

	public void setAudioPath(String audioPath) {
		_parseObject.put(AUDIO_PATH_KEY, audioPath);
	}

	public String getAudioPath() {
		return (String) _parseObject.get(AUDIO_PATH_KEY);
	}

	public int getAudioResource() {
		return _parseObject.getInt(AUDIO_RESOURCE_KEY);
	}

	public void setAudioResource(int audioResource) {
		_parseObject.put(AUDIO_RESOURCE_KEY, audioResource);
	}	
	
	public ParseFile getAudioFile() {
		return getParseFile(AUDIO);
	}

	public void setAudioFile(ParseFile file) {
		_parseObject.put(LATITUDE_KEY, latLng.latitude);
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
	
	public static ArrayList<Track> getTracks() {
		
		return null;
	}
}
