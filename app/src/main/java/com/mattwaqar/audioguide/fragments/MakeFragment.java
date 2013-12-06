package com.mattwaqar.audioguide.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.mattwaqar.audioguide.R;
import com.mattwaqar.audioguide.TrackArrayAdapter;
import com.mattwaqar.audioguide.client.Client;
import com.mattwaqar.audioguide.client.ItemsCallback;
import com.mattwaqar.audioguide.client.ParseClient;
import com.mattwaqar.audioguide.models.Track;

public class MakeFragment extends Fragment {
	
	private static final String TAG = "MakeFragment";
	
	private Client mClient;
	private TrackArrayAdapter mAdapter;
	private ListView lvTracks;
	private OnMakeSelectedListener mMakeListener;

	public interface OnMakeSelectedListener {
		public void onTrackSelected(Track track);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mClient = ParseClient.getInstance(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_make, container, false);
		
		lvTracks = (ListView) v.findViewById(R.id.lvTracks);
		mAdapter = new TrackArrayAdapter(getActivity(), new ArrayList<Track>());
		lvTracks.setAdapter(mAdapter);
		
		lvTracks.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				Track track = mAdapter.getItem(position);
				mMakeListener.onTrackSelected(track);
			}
			
		});
		
		lvTracks.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				Track track = mAdapter.getItem(position);
				track.deleteInBackground();
				mAdapter.remove(track);
				return true;
			}
			
		});

		return v;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		updateAdapter();		
	}
	
	public void updateAdapter() {
		mClient.getTracks(new ItemsCallback<Track>() {
			
			@Override
			public void onSuccess(List<Track> tracks) {
				mAdapter.clear();
				mAdapter.addAll(tracks);
			}

			@Override
			public void onFailure(Exception e) {
				Log.e(TAG, "Error retrieving list of tracks", e);				
			}
			
		});			
	}
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
        	mMakeListener = (OnMakeSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement MakeFragment.OnMakeSelectedListener");
        }
   }
}
