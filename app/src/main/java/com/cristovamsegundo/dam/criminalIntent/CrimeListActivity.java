package com.cristovamsegundo.dam.criminalIntent;

import android.support.v4.app.Fragment;

/**
 * Created by Cristovam on 08/10/2014.
 */
public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
