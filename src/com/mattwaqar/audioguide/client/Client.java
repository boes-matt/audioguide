package com.mattwaqar.audioguide.client;

import com.mattwaqar.audioguide.models.Track;

public interface Client {

	public void getTracks(ItemsCallback<Track> callback);
	public void getTrack(String id, ItemCallback<Track> callback);
	
}
