package com.mattwaqar.audioguide.fragments;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.mattwaqar.audioguide.AudioManager;
import com.mattwaqar.audioguide.R;
import com.mattwaqar.audioguide.models.Track;

public class MediaFragment extends Fragment implements OnClickListener {
	
	private static final String TAG = "MediaFragment";
	
	private Track mTrack;
	
	private Button btnRecord;
	private LinearLayout btnBarControls;
	private Button btnPlayStop;
	private boolean isPlaying;
	private Button btnDelete;
	
	private RecordListener mListener;
	
	public interface RecordListener {
		public void showRecordDialog();
		public String getLocalAudioPath();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_media, container, false);
		
		btnRecord = (Button) v.findViewById(R.id.btnRecord);
		btnBarControls = (LinearLayout) v.findViewById(R.id.btnBarControls);
		btnPlayStop = (Button) v.findViewById(R.id.btnPlayStop);
		btnDelete = (Button) v.findViewById(R.id.btnDelete);
		
		btnRecord.setOnClickListener(this);
		btnPlayStop.setOnClickListener(this);
		btnDelete.setOnClickListener(this);
		
		isPlaying = false;		
		if (mTrack.getAudioFile() == null) showRecord();
		else showPlayDelete();
		
		return v;
	}
	
	// Must call after instantiating class
	public void setTrack(Track track) {
		mTrack = track;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	    if (activity instanceof RecordListener) {
	        mListener = (RecordListener) activity;
	    } else {
	    	throw new ClassCastException(activity.toString() + " must implement MediaFragment.RecordListener");
	    }	
	}
	
	private void showRecord() {
		btnRecord.setVisibility(View.VISIBLE);
		btnBarControls.setVisibility(View.GONE);		
	}

	private void showPlayDelete() {
		btnRecord.setVisibility(View.GONE);
		btnBarControls.setVisibility(View.VISIBLE);		
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()) {
			case R.id.btnRecord:
				onRecord();
				break;
			case R.id.btnPlayStop:
				onPlayStop();
				break;
			case R.id.btnDelete:
				onDelete();
				break;
		}
	}
		
	public void onRecord() {
		mListener.showRecordDialog();
		showPlayDelete();
	}
	
	public void onPlayStop() {
		if (!isPlaying) {
			String audioPath = mListener.getLocalAudioPath();
			if (mTrack.getAudioFile() != null) {
				audioPath = mTrack.getAudioFile().getUrl();
			}
			
			AudioManager.playAudio(audioPath, new OnCompletionListener() {
				
				@Override
				public void onCompletion(MediaPlayer mp) {
					onPlayStop();
				}
				
			});
			btnPlayStop.setText("Stop");
			isPlaying = true;
		} else {
			AudioManager.stopAudio();
			btnPlayStop.setText("Play");
			isPlaying = false;
		}
	}
	
	public void onDelete() {
		mTrack.remove(Track.AUDIO);
		showRecord();
	}
	
}
