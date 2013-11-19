package com.mattwaqar.audioguide;

import java.util.List;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.model.LatLng;
import com.mattwaqar.audioguide.RemoteDataTask.OnQueryListener;
import com.mattwaqar.audioguide.fragments.DiscoverFragment;
import com.mattwaqar.audioguide.fragments.FragmentTabListener;
import com.mattwaqar.audioguide.fragments.MakeFragment;
import com.mattwaqar.audioguide.fragments.MakeFragment.OnMakeSelectedListener;
import com.mattwaqar.audioguide.models.Track;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class MainActivity extends FragmentActivity implements OnMakeSelectedListener, OnQueryListener {

	private static final int REQUEST_RECORD = 0;
	private static final int REQUEST_UPDATE = 1;
	private static final String PARSE_CLIENT_KEY = "79jBz0LksN6Gh1Q6dnr3HZKqpPTnuXRQJ7KWYc87";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setupNavigationTabs();
		
		setupParse();
	}

	private void setupParse() {
		Parse.initialize(this, PARSE_APPLICATION_ID, PARSE_CLIENT_KEY);
		ParseUser.enableAutomaticUser();
		ParseACL defaultACL = new ParseACL();
		// Optionally enable public read access.
		defaultACL.setPublicReadAccess(true);
		ParseACL.setDefaultACL(defaultACL, true);
	}

	private void setupNavigationTabs() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);

		Tab tabDiscover = actionBar.newTab().setText("Discover")
				.setTag("DiscoverFragment");
		tabDiscover.setTabListener(new FragmentTabListener<DiscoverFragment>(
				R.id.fragmentContainer, this, (String) tabDiscover.getTag(),
				DiscoverFragment.class));

		Tab tabMake = actionBar.newTab().setText("Make").setTag("MakeFragment");
		tabMake.setTabListener(new FragmentTabListener<MakeFragment>(
				R.id.fragmentContainer, this, (String) tabMake.getTag(),
				MakeFragment.class));

		actionBar.addTab(tabDiscover);
		actionBar.addTab(tabMake);
		actionBar.selectTab(tabDiscover);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void onRecord(MenuItem item) {
		Intent i = new Intent(this, RecordActivity.class);
		startActivityForResult(i, REQUEST_RECORD);
		overridePendingTransition(R.anim.right_in, R.anim.left_out);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == REQUEST_RECORD) {
				// Update occurs in fragment onResume 
				return;
			}
			
			if (requestCode == REQUEST_UPDATE) {
				// Update occurs in fragment onResume
				return;
			}
		}
	}

	@Override
	public void onTrackSelected(Track track) {
		Intent i = new Intent(this, RecordActivity.class);
		startActivityForResult(i, 1);
	}

	@Override
	public void showProgressDialog() {
		i.putExtra(RecordActivity.KEY_TRACK_ID, track.getObjectId());
	}

	@Override
	public void dismissProgressDialog() {
		startActivityForResult(i, REQUEST_UPDATE);
	}

	@Override
	public void onQueryStarted() {
		overridePendingTransition(R.anim.right_in, R.anim.left_out);

	@Override
	public void onQueryDidFinish(List<ParseObject> items) {
	}
	
	@Override
	private void addSampleTracks () {
		new RemoteDataTask(Track.CLASS_NAME, this) {
	protected void onPause() {
				Track barberShop = new Track("Barber shop", "Singing barbers",
						"Matt Boes", R.raw.sf_barber_shop,
						new LatLng(37.7749, -122.419));
				Track bayShore = new Track("Dock of the Bay", "Sitting on bay shore",
						"Matt Boes", R.raw.sf_bayshore, new LatLng(37.7549, -122.390));
				Track pacmanArcade = new Track("Pacman Arcade",
						"Along the Embarcadero", "Waqar Malik", R.raw.sf_pacman_arcade,
						new LatLng(37.7949, -122.400));
				Track queenMary = new Track("Queen Mary Ferry", "All aboard!",
						"Waqar Malik", R.raw.sf_queen_mary, new LatLng(37.8049,
								-122.419));
				try {
					barberShop.getParseObject().save();
					bayShore.getParseObject().save();
					pacmanArcade.getParseObject().save();
					queenMary.getParseObject().save();
				} catch (ParseException e) {
				}
		super.onPause();
				return null;
			}
		AudioManager.stopAudio();
	}
}
