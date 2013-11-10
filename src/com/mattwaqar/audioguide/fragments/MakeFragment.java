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
import com.mattwaqar.audioguide.RemoteDataTask;
import com.mattwaqar.audioguide.RemoteDataTask.OnQueryListener;
import com.mattwaqar.audioguide.TrackArrayAdapter;
import com.mattwaqar.audioguide.models.Track;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQuery.CachePolicy;

public class MakeFragment extends Fragment implements OnQueryListener {
	
	
	private static final String TAG = "MakeFragment";
	
	private TrackArrayAdapter mAdapter;
	private ListView lvTracks;
	private OnMakeSelectedListener mMakeListener;

	public interface OnMakeSelectedListener {
		public void onTrackSelected(Track track);
		public void showProgressDialog();
		public void dismissProgressDialog();
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

		new RemoteDataTask("Track", this).execute();
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				Track track = mAdapter.getItem(position);
				track.remove(Track.AUDIO);
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
		ParseQuery<ParseObject> query = ParseQuery.getQuery(Track.TAG);
		query.orderByDescending("createdAt");
		query.setCachePolicy(CachePolicy.NETWORK_ONLY);
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> tracks, ParseException e) {
				if (e != null) {
					Log.e(TAG, "Error retrieving list of tracks", e);
				} else {
					mAdapter.clear();
					for (ParseObject object : tracks)
						mAdapter.add((Track) object);
				}
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
	
	@Override
	public void onQueryStarted() {
		_makeListener.showProgressDialog();
	}

	@Override
	public void onQueryDidFinish(List<ParseObject> items) {
		for (ParseObject item : items) {
			Track track = new Track(item);
			_adapter.add(track);
		}
		_makeListener.dismissProgressDialog();
	}
}
