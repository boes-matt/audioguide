package com.mattwaqar.audioguide.fragments;

import java.io.IOException;
import java.util.Date;

import android.app.Activity;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mattwaqar.audioguide.R;

public class RecordDialog extends DialogFragment {
	
	private static final String TAG = "RecordDialog";
	
	private Button btnDone;
	private MediaRecorder mRecorder;
	private String mFilename;
	
	private MediaListener listener;
		
	public RecordDialog() {
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_record_audio, container, false);
		getDialog().setTitle(R.string.recording);
		
		btnDone = (Button) v.findViewById(R.id.btnDone);
		btnDone.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				stopRecording();
				listener.setAudio(mFilename);
				dismiss();
			}
			
		});

		return v;
	}

	private void startRecording() {
		Log.d(TAG, "mFilename: " + mFilename);
		
		mRecorder = new MediaRecorder();
		mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		mRecorder.setOutputFile(mFilename);
		mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		
		try {
			mRecorder.prepare();
		} catch (IOException e) {
			Log.e(TAG, "prepare() failed", e);
		}
		
		mRecorder.start();
	}
	
	private void stopRecording() {
		mRecorder.stop();
		mRecorder.release();
		mRecorder = null;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		if (activity instanceof MediaListener) {
			listener = (MediaListener) activity;
		} else {
			throw new ClassCastException(activity.toString() + " must implement MediaListener");
		}
		
		// Filename set to current timestamp
		Date d = new Date();				
		mFilename = getActivity().getExternalFilesDir(Environment.DIRECTORY_PODCASTS).toString();
		mFilename += "/" + String.valueOf(d.getTime());
		
		startRecording();
	}
	
}
