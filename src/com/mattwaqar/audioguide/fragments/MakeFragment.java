package com.mattwaqar.audioguide.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.mattwaqar.audioguide.R;
import com.mattwaqar.audioguide.RemoteDataTask;
import com.mattwaqar.audioguide.RemoteDataTask.OnQueryListener;
import com.mattwaqar.audioguide.TrackArrayAdapter;
import com.mattwaqar.audioguide.models.Track;
import com.parse.ParseObject;

public class MakeFragment extends Fragment implements OnQueryListener {
	private TrackArrayAdapter _adapter;
	private ListView _lvTracks;
	private OnMakeSelectedListener _makeListener;

	public interface OnMakeSelectedListener {
		public void onTrackSelected(Track track);
		public void showProgressDialog();
		public void dismissProgressDialog();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_make, container, false);
		
		_lvTracks = (ListView) v.findViewById(R.id.lvTracks);
		_adapter = new TrackArrayAdapter(getActivity(), new ArrayList<Track>());
		_lvTracks.setAdapter(_adapter);
		
		_lvTracks.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
				Track track = _adapter.getItem(position);
				_makeListener.onTrackSelected(track);
			}
		});

		new RemoteDataTask("Track", this).execute();
		return v;
	}
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
        	_makeListener = (OnMakeSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnTimelineSelectedListener");
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
