package com.mattwaqar.audioguide;

import java.io.File;
import java.io.IOException;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
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
			
			sPlayer.prepare();
		} catch (IOException e) {
			Log.e(TAG, "prepare() failed", e);
		}

		sPlayer.start();
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
