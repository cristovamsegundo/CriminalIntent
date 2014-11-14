package com.cristovamsegundo.dam.criminalIntent;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.Date;
import java.util.UUID;


public class CrimeFragment extends Fragment {
	private static final String TAG = "CrimeFragment";
    public static final String EXTRA_CRIME_ID = "com.cristovamsegundo.dam.android.criminalintent.crime_id";
    private static final String DIALOG_DATE = "date";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_PHOTO = 1;
	private Crime mCrime;
	private EditText mTitleView;
    private Button mDateButton;
    private CheckBox mSolvedCheckbox;
    private ImageButton mPhotoButton;
    private ImageView mPhotoView;

	public static CrimeFragment newInstance(UUID crimeID){
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_CRIME_ID, crimeID);

        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);

        return fragment;
    }

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		UUID crimeId = (UUID)getArguments().getSerializable(EXTRA_CRIME_ID);
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);

        setHasOptionsMenu(true);
	}

    @TargetApi(11)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.fragment_crime, parent, false);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            if(NavUtils.getParentActivityName(getActivity()) != null) {
                getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }
		
		mTitleView = (EditText) v.findViewById(R.id.crime_title);
        mTitleView.setText(mCrime.getTitle());
		mTitleView.addTextChangedListener(new TextWatcher(){
			public void onTextChanged(CharSequence c, int start, int before, int count) {
				mCrime.setTitle(c.toString());
			}
			
			public void beforeTextChanged(CharSequence c, int start, int count, int after) {
				// Space intentionally left blank
			}
			
			public void afterTextChanged(Editable c) {
				// This one too
			}
			
		});

        mDateButton = (Button) v.findViewById(R.id.crime_date);
        updateDate();
        mDateButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                FragmentManager fm = getActivity().getSupportFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mCrime.getDate());
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
                dialog.show(fm, DIALOG_DATE);
            }
        });

        mSolvedCheckbox = (CheckBox) v.findViewById(R.id.crime_solved);
        mSolvedCheckbox.setChecked(mCrime.isSolved());
        mSolvedCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                // Set the crime's solved property
                mCrime.setSolved(isChecked);
            }
        });
        
        mPhotoButton = (ImageButton) v.findViewById(R.id.crime_imageButton);
        mPhotoButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), CrimeCameraActivity.class);
				startActivityForResult(i, REQUEST_PHOTO);
				
			}
		});
        
        mPhotoView = (ImageView)v.findViewById(R.id.crime_imageView);
        
        // If camera not available, disable camera functionality
        PackageManager pm = getActivity().getPackageManager();
        if(!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA) && !pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)){
        	mPhotoButton.setEnabled(false);
        }
		
		return v;
	}
    
    private void showPhoto(){
    	Photo p = mCrime.getPhoto();
    	BitmapDrawable b = null;
    	if(p != null){
    		String path = getActivity().getFileStreamPath(p.getFilename()).getAbsolutePath();
    		b = PictureUtils.getScaledDrawable(getActivity(), path);
    	}
    	mPhotoView.setImageDrawable(b);
    }
    
    @Override
    public void onStart() {
    	super.onStart();
    	showPhoto();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode != Activity.RESULT_OK){
            return;
        }
        if(requestCode == REQUEST_DATE){
            Date date = (Date)data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mCrime.setDate(date);
            updateDate();
        } else if(requestCode == REQUEST_PHOTO){
        	// Create a new Photo object and attach it to the crime
        	String filename = data.getStringExtra(CrimeCameraFragment.EXTRA_PHOTO_FILENAME);
        	if(filename != null){
        		Photo p = new Photo(filename);
        		mCrime.setPhoto(p);
        		showPhoto();
        	}
        }
    }

    private void updateDate(){
        mDateButton.setText(mCrime.getDate().toString());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if(NavUtils.getParentActivityName(getActivity()) != null){
                    NavUtils.navigateUpFromSameTask(getActivity());
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    @Override
    public void onPause(){
    	super.onPause();
    	CrimeLab.get(getActivity()).saveCrimes();
    }

}
