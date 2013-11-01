package com.mattwaqar.audioguide.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

public class DiscoverFragment extends SupportMapFragment {
	
	private GoogleMap mGoogleMap;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = super.onCreateView(inflater, container, savedInstanceState);

		mGoogleMap = getMap();		
		mGoogleMap.setMyLocationEnabled(true);
		
		return v;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
//		Location loc = mGoogleMap.getMyLocation();
//		LatLng latLng = new LatLng(loc.getLatitude(), loc.getLongitude());
//		mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));		

	}

}
