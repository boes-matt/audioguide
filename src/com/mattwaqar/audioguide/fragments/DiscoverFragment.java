package com.mattwaqar.audioguide.fragments;

import java.util.ArrayList;
import java.util.HashMap;

import android.location.Location;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mattwaqar.audioguide.R;
import com.mattwaqar.audioguide.models.Track;

public class DiscoverFragment extends SupportMapFragment implements
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener {

	private static final String TAG = "DiscoverFragment";
	
	private GoogleMap mGoogleMap;
	private LocationClient mLocationClient;
	private Location mCurrentLocation;
	private ArrayList<Track> mTracks;
	private MediaPlayer mPlayer;
	private HashMap<String, Track> mMarkerTracks;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mLocationClient = new LocationClient(getActivity(), this, this);
		setupMockTracks();
	}

	private void setupMockTracks() {
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

		mTracks = new ArrayList<Track>();
		mTracks.add(barberShop);
		mTracks.add(bayShore);
		mTracks.add(pacmanArcade);
		mTracks.add(queenMary);
	}

	@Override
	public void onStart() {
		super.onStart();
		mLocationClient.connect();
	}

	@Override
	public void onStop() {
		super.onStop();
		mLocationClient.disconnect();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = super.onCreateView(inflater, container, savedInstanceState);

		mGoogleMap = getMap();
		mGoogleMap.setMyLocationEnabled(true);
		addTracksToMap(mTracks);
		setupOnClickHandlers();

		return v;
	}

	private void addTracksToMap(ArrayList<Track> tracks) {
		mMarkerTracks = new HashMap<String, Track>();

		for (Track track : tracks) {
			Marker marker = mGoogleMap.addMarker(new MarkerOptions()
					.position(track.getLatLng()).title(track.getTitle())
					.snippet(track.getDescription()));
			mMarkerTracks.put(marker.getId(), track);
		}
	}

	private void setupOnClickHandlers() {
		mGoogleMap.setOnMapClickListener(new OnMapClickListener() {

			@Override
			public void onMapClick(LatLng latLng) {
				stopAudio();
			}

		});

		mGoogleMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

			@Override
			public void onInfoWindowClick(Marker marker) {
				stopAudio();
				Track track = mMarkerTracks.get(marker.getId());
				if (track != null) {
					playAudio(track);
				} else {
					// TODO Why is track null so often on lookup in hashmap?!?
					Log.e(TAG, "Track is null for marker");
				}
			}

		});
	}

	private void playAudio(Track track) {
		if (mPlayer == null) {
			mPlayer = MediaPlayer.create(getActivity(), track.getAudioResource());
			mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

				@Override
				public void onCompletion(MediaPlayer mp) {
					stopAudio();
				}

			});
		}
		mPlayer.start();
	}

	private void stopAudio() {
		if (mPlayer != null) {
			mPlayer.release();
			mPlayer = null;
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
	}

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnected(Bundle dataBundle) {
		mCurrentLocation = mLocationClient.getLastLocation();
		if (mCurrentLocation != null) {
			LatLng latLng = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
			mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
		}
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub

	}

}
