package com.cristovamsegundo.dam.criminalIntent;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Cristovam on 08/10/2014.
 */
public class CrimeLab {
    private ArrayList<Crime> mCrimes;
    private static CrimeLab sCrimeLab;
    private Context mAppContext;
    
    private static final String TAG = "CrimeLab";
    private static final String FILENAME = "crimes.json";
    
    private CriminalIntentJSONSerializer mSerializer;

    private CrimeLab(Context appContext){ //Singleton pattern
        mAppContext = appContext;
        mSerializer = new CriminalIntentJSONSerializer(mAppContext, FILENAME);
        
        try{
        	mCrimes = mSerializer.loadCrimes();
        } catch (Exception e) {
        	mCrimes = new ArrayList<Crime>();
        	Log.e(TAG, "Error loading crimes: ", e);
        }
    }
    
    public static CrimeLab get(Context c){
        if(sCrimeLab == null){
            sCrimeLab = new CrimeLab(c.getApplicationContext());
        }
        return sCrimeLab;
    }

    public ArrayList<Crime> getCrimes(){
        return mCrimes;
    }

    public Crime getCrime(UUID id){
        for (Crime c:mCrimes){
            if(c.getId().equals(id)){
                return c;
            }
        }
        return null;
    }

    public void addCrime(Crime c){
        mCrimes.add(c);
    }
    
    public void deleteCrime(Crime c){
    	mCrimes.remove(c);
    }
    
    public boolean saveCrimes(){
    	try{
    		mSerializer.saveCrimes(mCrimes);
    		Log.d(TAG, "Crimes saved to file");
    		return true;
    	} catch (Exception e){
    		Log.e(TAG, "Error saving crimes: ", e);
    		return false;
    	}
    }
    
}
