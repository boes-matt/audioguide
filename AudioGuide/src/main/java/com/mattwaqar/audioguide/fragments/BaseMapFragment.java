package com.mattwaqar.audioguide.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

public abstract class BaseMapFragment extends SupportMapFragment implements
        GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener {

    private final static int PLAY_UNAVAILABLE_REQUEST = 9000;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9001;
    private final static String TAG = "BaseMapFragment";

    private LocationClient mLocationClient;
    private GoogleMap mGoogleMap;

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

        if (playServicesAvailable()) initMap();

        return v;
    }

    protected boolean playServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());
        if (status == ConnectionResult.SUCCESS) {
            Log.d(TAG, "Google Play services is available");
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, getActivity(), PLAY_UNAVAILABLE_REQUEST).show();
            return false;
        }
    }

    protected void initMap() {
        mGoogleMap = getMap();
        mGoogleMap.setMyLocationEnabled(true);
    }

    protected LocationClient getLocationClient() {
        return mLocationClient;
    }

    protected GoogleMap getGoogleMap() {
        return mGoogleMap;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String msg;
        switch (requestCode) {
            case PLAY_UNAVAILABLE_REQUEST:
                if (resultCode == Activity.RESULT_OK) {
                    msg = "Google Play is up to date now.";
                    initMap();
                }
                else msg = "Google Play unavailable request failed: " + resultCode;
                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                Log.d(TAG, msg);
                break;
            case CONNECTION_FAILURE_RESOLUTION_REQUEST:
                if (resultCode == Activity.RESULT_OK) msg = "Error resolved.  Client connected.";
                else msg = "Unable to resolve connection error.";
                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                Log.d(TAG, msg);
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onDisconnected() {
        Toast.makeText(getActivity(), "Disconnected from Google Maps service", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(getActivity(), CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getActivity(), "Failed connection to Google Maps service", Toast.LENGTH_SHORT).show();
            GooglePlayServicesUtil.getErrorDialog(connectionResult.getErrorCode(), getActivity(), CONNECTION_FAILURE_RESOLUTION_REQUEST).show();
        }
    }
}
