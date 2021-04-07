package com.zooverse.activities;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.zooverse.MainApplication;
import com.zooverse.R;
import com.zooverse.activities.adapters.SubjectLocationRecyclerViewAdapter;
import com.zooverse.zoo.Zoo;
import com.zooverse.zoo.Subject;

import java.util.TreeMap;

public class AroundMeActivity extends AbstractBaseActivity {
	private final SubjectLocationRecyclerViewAdapter subjectRecyclerViewAdapter = new SubjectLocationRecyclerViewAdapter();
	
	private static final int SEARCH_RADIUS = 99999999; //TODO: decide on sensible value (perhaps configurable per client?)
	private FusedLocationProviderClient locationProviderClient;
	private LocationCallback locationCallback;
	
	@Override
	@SuppressLint("MissingSuperCall")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.activity_subject_search, true);
		
		// Subject RecyclerView
		RecyclerView subjectList = findViewById(R.id.speciesList);
		subjectList.setAdapter(subjectRecyclerViewAdapter);
		subjectList.setLayoutManager(new LinearLayoutManager(this));
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		requestUserLocation();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
			locationProviderClient.removeLocationUpdates(locationCallback);
	}
	
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		if (requestCode == MainApplication.PERMISSION_REQUEST_LOCATION) {
			// If request is cancelled, the result arrays are empty.
			if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
					// Permission granted
					requestUserLocation();
			} else {
				// permission denied
				finish();
				Toast.makeText(this, getString(R.string.location_permission_denied), Toast.LENGTH_LONG).show();
			}
		}
	}
	
	public void requestUserLocation() {
		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
			LocationRequest locationRequest = LocationRequest.create();
			locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
			locationRequest.setInterval(10000);
			locationProviderClient = LocationServices.getFusedLocationProviderClient(this);
			
			locationCallback = new LocationCallback() {
				@Override
				public void onLocationResult(LocationResult locationResult) {
					// Update RecyclerView list of subjects based on userLocation
					Location userLocation = locationResult.getLastLocation();
					if (userLocation != null) {
						TreeMap<Integer, Subject> subjectsByDistance = new TreeMap<>();
						Location subjectLocation = new Location("");
						for (Subject subject : Zoo.getSubjectList()) {
							if (subject.getLocation() != null) {
								subjectLocation.setLatitude(subject.getLocation().first);
								subjectLocation.setLongitude(subject.getLocation().second);
								int distance = (int) userLocation.distanceTo(subjectLocation);
								if (distance < SEARCH_RADIUS) {
									subjectsByDistance.put(distance, subject);
								}
							}
						}
						subjectRecyclerViewAdapter.setSubjectListByDistance(subjectsByDistance);
					}
				}
			};
			locationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
		} else {
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MainApplication.PERMISSION_REQUEST_LOCATION);
		}
	}
}