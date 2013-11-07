package com.mattwaqar.audioguide.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.mattwaqar.audioguide.R;

public class MediaFragment extends Fragment implements OnClickListener {
	
	private static final String TAG = "MediaFragment";
	
	private Button btnRecord;
	private LinearLayout btnBarControls;
	private Button btnPlayStop;
	private boolean isPlaying;
	private Button btnDelete;
	
	private MediaListener listener;
		
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
		showRecord();
		
		return v;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		if (activity instanceof MediaListener) {
			listener = (MediaListener) activity;
		} else {
			throw new ClassCastException(activity.toString() + " must implement MediaListener");
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
	
	public void onRecord() {
		// Record audio
		showRecordDialog();
		showPlayDelete();
	}
	
	private void showRecordDialog() {
		FragmentManager fm = getActivity().getSupportFragmentManager();
		RecordDialog dialog = new RecordDialog();
		dialog.show(fm, "fragment_record_audio");
	}
	
	public void onPlayStop() {
		if (!isPlaying) {
			listener.playAudio();
			btnPlayStop.setText("Stop");
			isPlaying = true;
		} else {
			listener.stopAudio();
			btnPlayStop.setText("Play");
			isPlaying = false;
		}
	}
	
	public void onDelete() {
		listener.deleteAudio();
		showRecord();
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
}
