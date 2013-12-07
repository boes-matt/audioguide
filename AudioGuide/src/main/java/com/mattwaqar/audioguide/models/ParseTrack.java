package com.mattwaqar.audioguide.models;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.SaveCallback;

public class ParseTrack implements Track {

	/*
	 * A track is an audio track, GPS location, and meta-data.
	 */
	
	public static final String TAG = "Track";

	public static final String TITLE = "title";
	public static final String SUMMARY = "description";
	public static final String AUDIO = "audio";	
	public static final String LATITUDE = "latitude";
	public static final String LONGITUDE = "longitude";
	
	private ParseObject mObject;
	
	public ParseTrack() {
		mObject = new ParseObject(TAG);
	}
	
	public ParseTrack(ParseObject object) {
		mObject = object;
	}
	
	@Override
	public String getId() {
		return mObject.getObjectId();
	}
	
	@Override
	public String getTitle() {
		return mObject.getString(TITLE);
	}

	@Override
	public void setTitle(String title) {
		mObject.put(TITLE, title);
	}
	
	@Override
	public String getSummary() {
		return mObject.getString(SUMMARY);
	}
	
	@Override
	public void setSummary(String summary) {
		mObject.put(SUMMARY, summary);
	}
	
	@Override
	public String getAudioUri() {
		ParseFile file = mObject.getParseFile(AUDIO);
		if (file != null) return file.getUrl();
		else return null;
	}
	
	@Override
	public void saveAudio(byte[] audio) {
		final ParseFile file = new ParseFile("audiotrack.3gp", audio);
		file.saveInBackground(new SaveCallback() {
			
			@Override
			public void done(ParseException e) {
				if (e != null) Log.e(TAG, "Error saving audio file to server", e);
				else mObject.put(AUDIO, file);
			}
			
		});
	}
	
	@Override
	public void deleteAudio() {
		mObject.remove(AUDIO);
	}
		
	@Override	
	public LatLng getLatLng() {
		double lat = mObject.getDouble(LATITUDE);
		double lng = mObject.getDouble(LONGITUDE);
		if (lat == 0 || lng == 0) return null;
		else return new LatLng(lat, lng);
	}

	@Override
	public void setLatLng(LatLng latLng) {
		mObject.put(LATITUDE, latLng.latitude);
		mObject.put(LONGITUDE, latLng.longitude);
	}
	
	@Override
	public void saveInBackground() {
		mObject.saveInBackground();
	}
	
	@Override
	public void deleteInBackground() {
		deleteAudio();
		mObject.deleteInBackground();
	}
	
}
