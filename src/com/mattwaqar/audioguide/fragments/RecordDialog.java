package com.mattwaqar.audioguide.fragments;

import java.io.IOException;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mattwaqar.audioguide.AudioManager;
import com.mattwaqar.audioguide.R;
import com.mattwaqar.audioguide.models.Track;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.SaveCallback;

public class RecordDialog extends DialogFragment {
	
	private static final String TAG = "RecordDialog";
	
	private Track mTrack;
	private String mAudioPath;
	private Button btnDone;
	
	public RecordDialog() {
		
	}
	
	// Must call after instantiating class
	public void setupDialog(Track track, String audioPath) {
		mTrack = track;
		mAudioPath = audioPath;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AudioManager.startRecording(mAudioPath);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_record_audio, container, false);
		getDialog().setTitle(R.string.recording);
		
		btnDone = (Button) v.findViewById(R.id.btnDone);
		btnDone.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AudioManager.stopRecording();
				
				try {
					final ParseFile file = new ParseFile("audiotrack.3gp", AudioManager.getAudioBytes(mAudioPath));
					file.saveInBackground(new SaveCallback() {

						@Override
						public void done(ParseException e) {
							if (e != null) Log.e(TAG, "Error saving audio file to server", e);
							else mTrack.setAudioFile(file);
						}
						
					});
				} catch (IOException e) {
					Log.e(TAG, "Error converting audio file to bytes", e);
				}
				
				dismiss();
			}
			
		});

		return v;
	}
	
}
