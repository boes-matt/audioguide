package com.mattwaqar.audioguide.models;

import java.io.Serializable;

import com.google.android.gms.maps.model.LatLng;

public class Track implements Serializable {

	/*
	 * A track is an audio track, location, and meta-data.
	 */

	private String title;
	private String description;
	private String author;
	
	private int audioResource;
	private String audioPath;
	
	//private LatLng latLng;
	private double lat;
	private double lng;
		
	public Track(String title, String description, String author, int audioResource, LatLng latLng) {
		this.title = title;
		this.description = description;
		this.author = author;
		this.audioResource = audioResource;
		//this.latLng = latLng;
		this.lat = latLng.latitude;
		this.lng = latLng.longitude;
	}

	public Track(String title, String description, String author, String audioPath, LatLng latLng) {
		this.title = title;
		this.description = description;
		this.author = author;
		this.audioPath = audioPath;
		//this.latLng = latLng;
		this.lat = latLng.latitude;
		this.lng = latLng.longitude;
	}	
	
	/*
	public static String getPathFromResource(int resource) {
		// TODO: Does not work
		return Uri.parse("android.resource://" + "com.mattwaqar.audioguide/" + resource).toString();
	}
	*/
	
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
		//return latLng;
		return new LatLng(lat, lng);
	}

	public void setLatLng(LatLng latLng) {
		//this.latLng = latLng;
		lat = latLng.latitude;
		lng = latLng.longitude;
	}
	
	@Override
	public String toString() {
		return title;
	}

}
