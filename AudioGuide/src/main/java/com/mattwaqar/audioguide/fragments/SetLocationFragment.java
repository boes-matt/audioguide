package com.mattwaqar.audioguide.fragments;

import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.mattwaqar.audioguide.models.Track;

public class SetLocationFragment extends SupportMapFragment implements 
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener {

	private GoogleMap mGoogleMap;
	private LocationClient mLocationClient;
	private Location mTrackLocation;	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mLocationClient = new LocationClient(getActivity(), this, this);		
	}
	
	// Must call after instantiating class
	public void setTrackLocation(Track track) {
		LatLng latLng = track.getLatLng();
		if (latLng != null) {
			mTrackLocation = new Location("database");
			mTrackLocation.setLatitude(latLng.latitude);
			mTrackLocation.setLongitude(latLng.longitude);			
		}
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
	public void onConnectionFailed(ConnectionResult connectionResult) {
		Toast.makeText(getActivity(), "Failed connection to Google Maps service", Toast.LENGTH_SHORT).show();		
	}

	@Override
	public void onConnected(Bundle dataBundle) {
		// Null if not already assigned from database record
		if (mTrackLocation == null)
			mTrackLocation = mLocationClient.getLastLocation();
		
		// mTrackLocation now set either from database or current location
		if (mTrackLocation != null) {
			LatLng latLng = new LatLng(mTrackLocation.getLatitude(), mTrackLocation.getLongitude());
			mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
		}
	}

	@Override
	public void onDisconnected() {
		Toast.makeText(getActivity(), "Disconnected to Google Maps service", Toast.LENGTH_SHORT).show();		
	}
	
	public LatLng getTrackLocation() {
		LatLng center = mGoogleMap.getCameraPosition().target;
		return center;
	}
	
}
