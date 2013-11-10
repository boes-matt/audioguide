package com.mattwaqar.audioguide;

import java.util.List;

import android.os.AsyncTask;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class RemoteDataTask extends AsyncTask<Void, Void, Void> {
	private OnQueryListener _listener;
	private List<ParseObject> _items;
	private String _objectClass;
	
	public interface OnQueryListener {
		public void onQueryStarted();
		public void onQueryDidFinish(List <ParseObject> items);
	}

	public RemoteDataTask(String objectClass, OnQueryListener listener) {
		_objectClass = objectClass;
		_listener = listener;
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		// Gets the current list of in sorted order
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(_objectClass);
		query.orderByDescending("_created_at");

		try {
			_items = query.find();
		} catch (ParseException e) {

		}
		return null;
	}

	@Override
	protected void onPreExecute() {
		_listener.onQueryStarted();
		super.onPreExecute();
	}

	@Override
	protected void onProgressUpdate(Void... values) {
		super.onProgressUpdate(values);
	}

	@Override
	protected void onPostExecute(Void result) {
		_listener.onQueryDidFinish(_items);
	}
}
