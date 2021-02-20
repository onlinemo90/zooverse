package com.zooverse.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.zooverse.MainApplication;
import com.zooverse.R;
import com.zooverse.activities.adapters.SubjectCatalogueLocationAdapter;

public class SubjectCatalogueLocationActivity extends AbstractSubjectCatalogueActivity {
	
	private FusedLocationProviderClient locationProviderClient;
	private LocationCallback locationCallback;
	private Location userLocation;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		subjectCatalogueAdapter = new SubjectCatalogueLocationAdapter(this);
		setContentView(R.layout.activity_subject_catalogue);
		super.onCreate(savedInstanceState);
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
	
	
	public void requestUserLocation() {
		//if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
			//Location Permission already granted, initial check is handled in activity
			
			LocationRequest locationRequest = LocationRequest.create();
			locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
			locationRequest.setInterval(10000);
			
			locationCallback = new LocationCallback() {
				@Override
				public void onLocationResult(LocationResult locationResult) {
					userLocation = locationResult.getLastLocation();
					subjectCatalogueAdapter.updateCursor(userLocation);
					subjectCatalogueAdapter.notifyDataSetChanged();
				}
			};
			
			locationProviderClient = LocationServices.getFusedLocationProviderClient(this);
			locationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
		} else {
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MainApplication.PERMISSION_REQUEST_LOCATION);
		}
	}
	
	@Override
	public void onRequestPermissionsResult(int requestCode, String [] permissions, int[] grantResults) {
		if (requestCode == MainApplication.PERMISSION_REQUEST_LOCATION) {
			// If request is cancelled, the result arrays are empty.
			if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
					// Permission granted
					requestUserLocation();
			}
			else {
				// permission denied
				finish();
				Toast.makeText(this, getString(R.string.location_permission_denied), Toast.LENGTH_LONG).show();
			}
		}
	}
	
}