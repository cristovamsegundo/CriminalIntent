package com.cristovamsegundo.dam.criminalIntent;

import java.util.UUID;

import android.util.Log;

public class Crime {
	private UUID mId;
	private String mTitle;
	private static final String TAG = "CrimeActivity";
	
	public Crime() {
		// Generate unique identifier
		mId = UUID.randomUUID() ;
	}

	public UUID getId() {
		return mId;
	}

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String mTitle) {
		this.mTitle = mTitle;
		Log.d(TAG, mTitle);
	}
	

}
