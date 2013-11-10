package com.mattwaqar.audioguide.models;

import java.io.Serializable;

import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseObject;

public class Track implements Serializable {
	private static final long serialVersionUID = 4341445389961887628L;
	/*
	 * A track is an audio track, location, and meta-data.
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
	
	public Track(String title, String description, String author, int audioResource, LatLng latLng) {
		_parseObject = new ParseObject(CLASS_NAME);
		setTitle(title);
		setDescription(description);
		setAuthor(author);
		setAudioResource(audioResource);
		setLocation(latLng);
	}

	public Track(String title, String description, String author, String audioPath, LatLng latLng) {
		_parseObject = new ParseObject(CLASS_NAME);
		setTitle(title);
		setDescription(description);
		setAuthor(author);
		setAudioPath(audioPath);
		setLocation(latLng);
	}	
	
	/*
	public static String getPathFromResource(int resource) {
		// TODO: Does not work
		return Uri.parse("android.resource://" + "com.mattwaqar.audioguide/" + resource).toString();
	}
	*/
	
	public String getTitle() {
		return (String) _parseObject.get(TITLE_KEY);
	}

	public void setTitle(String title) {
		_parseObject.put(TITLE_KEY, title);
	}

	public String getDescription() {
		return (String) _parseObject.get(DESCRIPTION_KEY);
	}

	public void setDescription(String description) {
		_parseObject.put(DESCRIPTION_KEY, description);
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
	
	public LatLng getLocation() {
		return new LatLng(_parseObject.getDouble(LATITUDE_KEY), _parseObject.getDouble(LONGITUDE_KEY));
	}

	public void setLocation(LatLng latLng) {
		_parseObject.put(LATITUDE_KEY, latLng.latitude);
		_parseObject.put(LONGITUDE_KEY, latLng.longitude);
	}
	
	@Override
	public String toString() {
		return getTitle();
	}

	public ParseObject getParseObject() {
		return _parseObject;
	}

	public void setParseObject(ParseObject _parseObject) {
		this._parseObject = _parseObject;
	}

}
