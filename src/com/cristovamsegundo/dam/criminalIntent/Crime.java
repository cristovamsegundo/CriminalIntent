package com.cristovamsegundo.dam.criminalIntent;

import java.util.Date;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Crime {
	private UUID mId;
	private String mTitle;
    private Date mDate;
    private boolean mSolved;
    private Photo mPhoto;

	private static final String TAG = "CrimeActivity";
	
	// JSON Conversion
	private static final String JSON_ID = "id";
	private static final String JSON_TITLE = "title";
	private static final String JSON_SOLVED = "solved";
	private static final String JSON_DATE = "date";
	private static final String JSON_PHOTO = "photo";
	
	public Crime() {
		// Generate unique identifier
		mId = UUID.randomUUID() ;
        // Creates a Date object with the date when it's created
        mDate = new Date();
	}
	
    public Crime(JSONObject json) throws JSONException{
    	mId = UUID.fromString(json.getString(JSON_ID));
    	mTitle = json.getString(JSON_TITLE);
    	mSolved = json.getBoolean(JSON_SOLVED);
    	mDate = new Date(json.getLong(JSON_DATE));
    	if(json.has(JSON_PHOTO)){
    		mPhoto = new Photo(json.getJSONObject(JSON_PHOTO));
    	}
    }
    
    public JSONObject toJSON() throws JSONException {
    	JSONObject json = new JSONObject();
    	json.put(JSON_ID, mId.toString());
    	json.put(JSON_TITLE, mTitle);
    	json.put(JSON_SOLVED, mSolved);
    	json.put(JSON_DATE, mDate.getTime());
    	if(mPhoto != null){
    		json.put(JSON_PHOTO, mPhoto.toJSON());
    	}
    	return json;
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

    public void setDate(Date mDate) {
        this.mDate = mDate;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean mSolved) {
        this.mSolved = mSolved;
    }

    @Override
    public String toString(){
        return mTitle;
    }
    
    public Photo getPhoto(){
    	return mPhoto;
    }
    
    public void setPhoto(Photo p){
    	mPhoto = p;
    }
}
