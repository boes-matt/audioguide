package com.mattwaqar.audioguide.fragments;

import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnected(Bundle dataBundle) {
		mTrackLocation = mLocationClient.getLastLocation();
		if (mTrackLocation != null) {
			LatLng latLng = new LatLng(mTrackLocation.getLatitude(), mTrackLocation.getLongitude());
			mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
		}
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}
	
	public LatLng getTrackLatLng() {
		//return new LatLng(mTrackLocation.getLatitude(), mTrackLocation.getLongitude());
		LatLng center = mGoogleMap.getCameraPosition().target;
		return center;
	}
	
}
