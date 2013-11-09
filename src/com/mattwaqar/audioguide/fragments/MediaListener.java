package com.mattwaqar.audioguide.fragments;

public interface MediaListener {

	public void setAudio(String trackPath);
	public void deleteAudio();
	public void playAudio();
	public void stopAudio();
}
