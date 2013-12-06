package com.mattwaqar.audioguide.client;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.mattwaqar.audioguide.R;
import com.mattwaqar.audioguide.models.ParseTrack;
import com.mattwaqar.audioguide.models.Track;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQuery.CachePolicy;
import com.parse.ParseUser;

public class ParseClient implements Client {

	protected static final String TAG = "ParseClient";
	
	static Client sClient;
	
	private ParseClient(Context appContext) {
		Parse.initialize(appContext, appContext.getString(R.string.parse_app_id), 
				appContext.getString(R.string.parse_client_key));
		
		ParseUser.enableAutomaticUser();
		
		ParseACL defaultACL = new ParseACL();
		defaultACL.setPublicReadAccess(true);
		defaultACL.setPublicWriteAccess(true);
		ParseACL.setDefaultACL(defaultACL, true);
	}
	
	public static Client getInstance(Context context) {
		if (sClient != null) {
			return sClient;
		} else {
			return new ParseClient(context.getApplicationContext());		
		}
	}

	@Override
	public void getTracks(final ItemsCallback<Track> callback) {
		ParseQuery<ParseObject> query = ParseQuery.getQuery(ParseTrack.TAG);
		query.orderByDescending("createdAt");
		query.setCachePolicy(CachePolicy.NETWORK_ONLY);
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				if (e != null) {
					callback.onFailure(e);
				} else {
					List<Track> tracks = new ArrayList<Track>();
					for (ParseObject object : objects)
						tracks.add(new ParseTrack(object));
					callback.onSuccess(tracks);					
				}
			}
			
		});
	}

	@Override
	public void getTrack(String id, final ItemCallback<Track> callback) {
		ParseQuery<ParseObject> query = ParseQuery.getQuery(ParseTrack.TAG);
		query.setCachePolicy(CachePolicy.NETWORK_ONLY);
		query.getInBackground(id, new GetCallback<ParseObject>() {
			
			@Override
			public void done(ParseObject object, ParseException e) {
				if (e != null) callback.onFailure(e);
				else callback.onSuccess(new ParseTrack(object));
			}
			
		});
	}

}
