package com.mattwaqar.audioguide;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.mattwaqar.audioguide.fragments.DiscoverFragment;
import com.mattwaqar.audioguide.fragments.FragmentTabListener;
import com.mattwaqar.audioguide.fragments.MakeFragment;

public class MainActivity extends FragmentActivity {

	private static final int REQUEST_RECORD = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setupNavigationTabs();
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
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == REQUEST_RECORD) {
				// TODO Get new track if not null.  Add to mTracks and marker to map.
			}
		}
	}
	
}
