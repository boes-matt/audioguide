package com.mattwaqar.audioguide.models;

import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;
import com.mattwaqar.audioguide.R;

public class Track {

	/*
	 * A track is an audio track, location, and meta-data.
	 */

	private String title;
	private String description;
	private String author;
	
	private int audioResource;
	private String audioPath;
	
	private LatLng latLng;
		
	public Track(String title, String description, String author, int audioResource, LatLng latLng) {
		this.title = title;
		this.description = description;
		this.author = author;
		this.audioResource = audioResource;
		this.latLng = latLng;
	}

	public Track(String title, String description, String author, String audioPath, LatLng latLng) {
		this.title = title;
		this.description = description;
		this.author = author;
		this.audioPath = audioPath;
		this.latLng = latLng;
	}	
	
	public String getPathFromResource(int resource) {
		// TODO: Works??
		return Uri.parse("android.resource://" + "com.mattwaqar.audioguide/" + resource).toString();
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getAudioResource() {
		return audioResource;
	}

	public void setAudioPath(String audioPath) {
		this.audioPath = audioPath;
	}

	public String getAudioPath() {
		return audioPath;
	}

	public void setAudioResource(int audioResource) {
		this.audioResource = audioResource;
	}	
	
	public LatLng getLatLng() {
		return latLng;
	}

	public void setLatLng(LatLng latLng) {
		this.latLng = latLng;
	}
	
	@Override
	public String toString() {
		return title;
	}

}
