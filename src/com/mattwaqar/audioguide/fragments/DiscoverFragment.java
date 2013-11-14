package com.mattwaqar.audioguide.fragments;

import java.util.HashMap;
import java.util.List;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mattwaqar.audioguide.AudioManager;
import com.mattwaqar.audioguide.R;
import com.mattwaqar.audioguide.models.Track;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQuery.CachePolicy;

public class DiscoverFragment extends SupportMapFragment implements
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener {

	private static final String TAG = "DiscoverFragment";
	
	private GoogleMap mGoogleMap;
	private LocationClient mLocationClient;
	private BitmapDescriptor markerIcon;
	private Location mCurrentLocation;
	private HashMap<String, Track> mMarkerTracks;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mLocationClient = new LocationClient(getActivity(), this, this);
		markerIcon = BitmapDescriptorFactory.fromResource(R.drawable.ic_listen_track);
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = super.onCreateView(inflater, container, savedInstanceState);

		mGoogleMap = getMap();
		mGoogleMap.setMyLocationEnabled(true);
		
		return v;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		updateMapTracks();		
	}

	public void updateMapTracks() {
		ParseQuery<ParseObject> query = ParseQuery.getQuery(Track.TAG);
		query.setCachePolicy(CachePolicy.NETWORK_ONLY);
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> tracks, ParseException e) {
				if (e != null) {
					Log.e(TAG, "Error retrieving list of tracks", e);
				} else {
					mGoogleMap.clear();	
					mMarkerTracks = new HashMap<String, Track>();
										
					for (ParseObject object : tracks) {
						Track track = (Track) object;
						Marker marker = mGoogleMap.addMarker(new MarkerOptions()
								.position(track.getLatLng()).title(track.getTitle())
								.snippet(track.getDescription())
								.icon(markerIcon));
						mMarkerTracks.put(marker.getId(), track);
					}
					
					setupMarkerOnClickHandlers();			
				}
			}
			
		});
		
	}

	private void setupMarkerOnClickHandlers() {
		mGoogleMap.setOnMapClickListener(new OnMapClickListener() {

			@Override
			public void onMapClick(LatLng latLng) {
				AudioManager.stopAudio();
			}

		});

		mGoogleMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

			@Override
			public void onInfoWindowClick(Marker marker) {								
				AudioManager.stopAudio();
				Track track = mMarkerTracks.get(marker.getId());
				AudioManager.playAudio(track.getAudioFile().getUrl(), null);
			}

		});
	}

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		Toast.makeText(getActivity(), "Failed connection to Google Maps service", Toast.LENGTH_SHORT).show();
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
		Toast.makeText(getActivity(), "Disconnected to Google Maps service", Toast.LENGTH_SHORT).show();
	}
	
}
