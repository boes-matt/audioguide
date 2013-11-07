package com.mattwaqar.audioguide.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;
import com.mattwaqar.audioguide.R;
import com.mattwaqar.audioguide.TrackArrayAdapter;
import com.mattwaqar.audioguide.models.Track;

public class MakeFragment extends Fragment {
	private TrackArrayAdapter _adapter;
	private ListView _lvTracks;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_make, container, false);
		
		_lvTracks = (ListView) v.findViewById(R.id.lvTracks);
		_adapter = new TrackArrayAdapter(getActivity(), new ArrayList<Track>());
		_lvTracks.setAdapter(_adapter);

		Track barberShop = new Track("Barber shop", "Singing barbers",
				"Matt Boes", R.raw.sf_barber_shop,
				new LatLng(37.7749, -122.419));
		_adapter.add(barberShop);
		Track bayShore = new Track("Dock of the Bay", "Sitting on bay shore",
				"Matt Boes", R.raw.sf_bayshore, new LatLng(37.7549, -122.390));
		_adapter.add(bayShore);
		Track pacmanArcade = new Track("Pacman Arcade",
				"Along the Embarcadero", "Waqar Malik", R.raw.sf_pacman_arcade,
				new LatLng(37.7949, -122.400));
		_adapter.add(pacmanArcade);
		Track queenMary = new Track("Queen Mary Ferry", "All aboard!",
				"Waqar Malik", R.raw.sf_queen_mary, new LatLng(37.8049,
						-122.419));
		_adapter.add(queenMary);
		return v;
	}
}
