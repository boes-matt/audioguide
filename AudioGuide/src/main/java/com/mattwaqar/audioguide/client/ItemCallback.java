package com.mattwaqar.audioguide.client;

public interface ItemCallback<T> {

	public void onSuccess(T item);
	public void onFailure(Exception e);
	
}
