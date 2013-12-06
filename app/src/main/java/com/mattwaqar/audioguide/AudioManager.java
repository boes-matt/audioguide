package com.mattwaqar.audioguide;

import java.io.File;
import java.io.IOException;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaRecorder;
import android.util.Log;

import com.google.common.io.Files;

public class AudioManager {

	private static final String TAG = "AudioManager";
	private static MediaPlayer sPlayer;
	private static MediaRecorder sRecorder;
		
	public static void playAudio(String audioPath, OnCompletionListener listener) {
		if (sPlayer == null) 
			sPlayer = new MediaPlayer();
		
		try {
			sPlayer.setDataSource(audioPath);	
			sPlayer.setAudioStreamType(android.media.AudioManager.STREAM_MUSIC);
			sPlayer.setOnBufferingUpdateListener(new OnBufferingUpdateListener() {
				
				@Override
				public void onBufferingUpdate(MediaPlayer mp, int percent) {
					// TODO FIX: Parse needs to set audio byte size in header?
					Log.d(TAG, "Percent buffered: " + percent);
				}
				
			});
			
			
			if (listener == null) {
				listener = new OnCompletionListener() {

					@Override
					public void onCompletion(MediaPlayer mp) {
						stopAudio();
					}
					
				};
			}
			// If not null, passed listener should call stopAudio(), and if necessary, update UI in activity or fragment.
			sPlayer.setOnCompletionListener(listener);
			
			sPlayer.setOnPreparedListener(new OnPreparedListener() {
				
				@Override
				public void onPrepared(MediaPlayer mp) {
					mp.start();
				}
				
			});
			
			sPlayer.prepareAsync();
		} catch (IOException e) {
			Log.e(TAG, "prepare() failed", e);
		}
	}
	
	public static void stopAudio() {
		if (sPlayer != null) {
			sPlayer.stop();
			sPlayer.release();
			sPlayer = null;
		}
	}	
	
	public static void startRecording(String audioPath) {
		if (sRecorder == null)
			sRecorder = new MediaRecorder();
		
		try {
			sRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			sRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
			sRecorder.setOutputFile(audioPath);
			sRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
			
			sRecorder.prepare();
		} catch (IOException e) {
			Log.e(TAG, "prepare() failed", e);
		}
		
		sRecorder.start();
	}
	
	public static void stopRecording() {
		if (sRecorder != null) {
			sRecorder.stop();
			sRecorder.release();
			sRecorder = null;			
		}
	}
	
	public static byte[] getAudioBytes(String audioPath) throws IOException {
		return Files.toByteArray(new File(audioPath));
	}
	
	public static void deleteRecording(String audioPath) {
			File audio = new File(audioPath);
			boolean success = audio.delete();
			Log.d(TAG, "Audio file deleted? " + success);
	}
	
}
