package com.mattwaqar.audioguide;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.widget.EditText;
import android.widget.Toast;

import com.mattwaqar.audioguide.client.Client;
import com.mattwaqar.audioguide.client.ItemCallback;
import com.mattwaqar.audioguide.client.ParseClient;
import com.mattwaqar.audioguide.fragments.MediaFragment;
import com.mattwaqar.audioguide.fragments.MediaFragment.RecordListener;
import com.mattwaqar.audioguide.fragments.RecordDialog;
import com.mattwaqar.audioguide.fragments.SetLocationFragment;
import com.mattwaqar.audioguide.models.ParseTrack;
import com.mattwaqar.audioguide.models.Track;

public class RecordActivity extends FragmentActivity implements RecordListener {

	public static final String TAG = "RecordActivity";
	public static final String KEY_TRACK_ID = "track_id";

	private Client mClient;
	
	private EditText etTitle;
	private EditText etDescription;

	private MediaFragment mMediaFragment;
	private SetLocationFragment mSetLocationFragment;	

	private String TEMP_AUDIO_PATH;
	private Track mTrack;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_record);
		mClient = ParseClient.getInstance(this);
		etTitle = (EditText) findViewById(R.id.etTitle);
		etDescription = (EditText) findViewById(R.id.etDescription);
		TEMP_AUDIO_PATH = getExternalFilesDir(Environment.DIRECTORY_PODCASTS).toString() + "/audiotrack.3gp";
		loadTrack();
	}
	
	private void loadTrack() {
		String trackId = getIntent().getStringExtra(KEY_TRACK_ID);
		if (trackId == null) {
			mTrack = new ParseTrack();
			loadFragments();
		} else {
			mClient.getTrack(trackId, new ItemCallback<Track>() {
				
				@Override
				public void onSuccess(Track track) {																																	
					mTrack = track;
					etTitle.setText(mTrack.getTitle());
					etDescription.setText(mTrack.getDescription());						
					loadFragments();
				}

				@Override
				public void onFailure(Exception e) {
					Log.e(TAG, "Error retrieving track", e);
					mTrack = new ParseTrack();
					loadFragments();
				}
				
			});		
		}
	}

	private void loadFragments() {
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		
		mMediaFragment = new MediaFragment();
		mMediaFragment.setTrack(mTrack);
		ft.replace(R.id.mediaFragment, mMediaFragment);
		
		mSetLocationFragment = new SetLocationFragment();
		mSetLocationFragment.setTrackLocation(mTrack);
		ft.replace(R.id.setLocationFragment, mSetLocationFragment);
		
		ft.commit();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.record, menu);
		return true;
	}
	
	@Override
	public void showRecordDialog() {
		RecordDialog dialog = new RecordDialog();
		dialog.setupDialog(mTrack, TEMP_AUDIO_PATH);
		dialog.show(getSupportFragmentManager(), "fragment_record_audio");
	}
	
	@Override
	public String getLocalAudioPath() {
		return TEMP_AUDIO_PATH;
	}
	
	@Override
	public void onBackPressed() {
		mTrack.setTitle(etTitle.getText().toString());
		mTrack.setDescription(etDescription.getText().toString());
		mTrack.setLatLng(mSetLocationFragment.getTrackLocation());
		
		String EMPTY = "";
		
		if (!mTrack.getTitle().equals(EMPTY) && 
			!mTrack.getDescription().equals(EMPTY) && 
			mTrack.getAudioUri() != null) {
			
			Log.d(TAG, "Track is: " + mTrack.getId() + ", " + mTrack.getTitle() + ", " + mTrack.getDescription());
			
			mTrack.saveInBackground();
			Toast.makeText(this, "Saving track", Toast.LENGTH_SHORT).show();
			setResult(Activity.RESULT_OK);			
		} else {
			Toast.makeText(this, "Track not saved", Toast.LENGTH_SHORT).show();
			setResult(Activity.RESULT_CANCELED);							
		}
		
		super.onBackPressed();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		AudioManager.stopAudio();
	}
		
}
