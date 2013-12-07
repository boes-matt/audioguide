package com.mattwaqar.audioguide.fragments;

import java.util.HashMap;
import java.util.List;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mattwaqar.audioguide.AudioManager;
import com.mattwaqar.audioguide.R;
import com.mattwaqar.audioguide.client.Client;
import com.mattwaqar.audioguide.client.ItemsCallback;
import com.mattwaqar.audioguide.client.ParseClient;
import com.mattwaqar.audioguide.models.Track;

public class DiscoverFragment extends BaseMapFragment {

	private static final String TAG = "DiscoverFragment";
	
	private Client mClient;
	private BitmapDescriptor markerIcon;
	private Location mCurrentLocation;
	private HashMap<String, Track> mMarkerTracks;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mClient = ParseClient.getInstance(getActivity());
		markerIcon = BitmapDescriptorFactory.fromResource(R.drawable.ic_listen_track);
	}

	@Override
	public void onResume() {
		super.onResume();
		updateMapTracks();		
	}

	public void updateMapTracks() {
		mClient.getTracks(new ItemsCallback<Track>() {

			@Override
			public void onSuccess(List<Track> tracks) {
				getGoogleMap().clear();
				mMarkerTracks = new HashMap<String, Track>();
				
				for (Track track : tracks) {
					Marker marker = getGoogleMap().addMarker(new MarkerOptions()
                            .position(track.getLatLng()).title(track.getTitle())
                            .snippet(track.getSummary())
                            .icon(markerIcon));
					mMarkerTracks.put(marker.getId(), track);
				}
				
				setupMarkerOnClickHandlers();
			}

			@Override
			public void onFailure(Exception e) {
				Log.e(TAG, "Error retrieving list of tracks", e);				
			}
			
		});		
	}

	private void setupMarkerOnClickHandlers() {
		getGoogleMap().setOnMapClickListener(new OnMapClickListener() {

            @Override
            public void onMapClick(LatLng latLng) {
                AudioManager.stopAudio();
            }

        });

		getGoogleMap().setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker marker) {
                AudioManager.stopAudio();
                Track track = mMarkerTracks.get(marker.getId());
                AudioManager.playAudio(track.getAudioUri(), null);
            }

        });
	}

	@Override
	public void onConnected(Bundle dataBundle) {
		if (playServicesAvailable())
            mCurrentLocation = getLocationClient().getLastLocation();

        if (mCurrentLocation != null) {
			LatLng latLng = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
			getGoogleMap().moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
		}
	}

}
