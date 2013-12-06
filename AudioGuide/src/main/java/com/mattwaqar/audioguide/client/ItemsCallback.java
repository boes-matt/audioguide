package com.mattwaqar.audioguide.client;

import java.util.List;

public interface ItemsCallback<T> {

	public void onSuccess(List<T> items);
	public void onFailure(Exception e);
	
}
