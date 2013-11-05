package com.mattwaqar.audioguide.models;

import com.google.android.gms.maps.model.LatLng;

public class Track {

	/*
	 * A track is an audio track, location, and meta-data.
	 */

	private String title;
	private String description;
	private String author;
	private int audioResource;
	private LatLng latLng;
		
	public Track(String title, String description, String author, int audioResource, LatLng latLng) {
		this.title = title;
		this.description = description;
		this.author = author;
		this.audioResource = audioResource;
		this.latLng = latLng;
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
