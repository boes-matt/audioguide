package com.mattwaqar.audioguide;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.EditText;

import com.google.android.gms.maps.model.LatLng;
import com.mattwaqar.audioguide.fragments.MediaFragment;
import com.mattwaqar.audioguide.fragments.MediaListener;
import com.mattwaqar.audioguide.fragments.SetLocationFragment;
import com.mattwaqar.audioguide.models.Track;

public class RecordActivity extends FragmentActivity implements MediaListener {

	private static final String TAG = "RecordActivity";
	
	private EditText etTitle;
	private EditText etDescription;
	private String mTrackPath;
	private LatLng mLatLng;
	
	private MediaPlayer mPlayer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_record);

		etTitle = (EditText) findViewById(R.id.etTitle);
		etDescription = (EditText) findViewById(R.id.etDescription);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.record, menu);
		return true;
	}

	@Override
	public void onBackPressed() {
		String title = etTitle.getText().toString();
		String description = etDescription.getText().toString();
		String author = "Matt Waqar";

		SetLocationFragment fragment = (SetLocationFragment) getSupportFragmentManager().findFragmentById(R.id.mapView);
		mLatLng = fragment.getTrackLatLng();
		
		Track track = new Track(title, description, author, mTrackPath, mLatLng);
		Intent i = new Intent();
		i.putExtra("Track", track);
		setResult(Activity.RESULT_OK, i);
		
		super.onBackPressed();
	}

	@Override
	public void setAudio(String trackPath) {
		mTrackPath = trackPath;
	}

	@Override
	public void deleteAudio() {
		File audio = new File(mTrackPath);
		audio.delete();
		mTrackPath = null;
	}

	@Override
	public void playAudio() {
		mPlayer = new MediaPlayer();
		try {
			mPlayer.setDataSource(mTrackPath);
			mPlayer.setOnCompletionListener(new OnCompletionListener() {
				
				@Override
				public void onCompletion(MediaPlayer mp) {
					MediaFragment fragment = (MediaFragment) getSupportFragmentManager().findFragmentById(R.id.mcRecordPlay);
					fragment.onPlayStop();
				}
			});
			
			mPlayer.prepare();
			mPlayer.start();
		} catch (IOException e) {
			Log.e(TAG, "prepare() failed", e);
		}
	}

	@Override
	public void stopAudio() {
		mPlayer.release();
		mPlayer = null;
	}
		
}
