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

public class SetLocationFragment extends BaseMapFragment {

	private Location mTrackLocation;
	
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
	public void onConnected(Bundle dataBundle) {
        if (!playServicesAvailable()) return;

		// Null if not already assigned from database record
		if (mTrackLocation == null)
			mTrackLocation = getLocationClient().getLastLocation();
		
		// mTrackLocation now set either from database or current location
		if (mTrackLocation != null) {
			LatLng latLng = new LatLng(mTrackLocation.getLatitude(), mTrackLocation.getLongitude());
			getGoogleMap().moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
		}
	}

	public LatLng getTrackLocation() {
		LatLng center = getGoogleMap().getCameraPosition().target;
		return center;
	}
	
}
