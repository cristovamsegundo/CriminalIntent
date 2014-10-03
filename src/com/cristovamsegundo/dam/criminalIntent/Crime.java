package com.cristovamsegundo.dam.criminalIntent;

import java.util.Date;
import java.util.UUID;

import android.util.Log;

public class Crime {
	private UUID mId;
	private String mTitle;
	private Date mDate;
	private boolean mSolved;
	private static final String TAG = "CrimeActivity";
	
	public Crime() {
		// Generate unique identifier
		mId = UUID.randomUUID() ;
		// Gets the current date
		mDate = new Date();
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

	public Date getDate() {
		return mDate;
	}

	public void setDate(Date date) {
		this.mDate = date;
	}

	public boolean ismSolved() {
		return mSolved;
	}

	public void setmSolved(boolean solved) {
		this.mSolved = solved;
	}
	
	
	

}
