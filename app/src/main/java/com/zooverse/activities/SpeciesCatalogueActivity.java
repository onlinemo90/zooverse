package com.zooverse.activities;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.zooverse.MainApplication;
import com.zooverse.R;
import com.zooverse.activities.adapters.SpeciesCatalogueAdapter;

public class SpeciesCatalogueActivity extends AbstractBaseActivity implements SpeciesCatalogueAdapter.SpeciesOnClickListener {
	public static final int CATALOGUE_MODE_SEARCH = 0;
	public static final int CATALOGUE_MODE_TOUR = 1;
	private SpeciesCatalogueAdapter speciesCatalogueAdapter;
	private FusedLocationProviderClient locationProviderClient;
	private LocationCallback locationCallback;
	private Location userLocation;
	private int catalogueMode;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_species_catalogue);
		catalogueMode = getIntent().getIntExtra(MainApplication.INTENT_EXTRA_SPECIES_CATALOGUE_MODE,0);
		this.enableOptionsMenu();
		
		speciesCatalogueAdapter = new SpeciesCatalogueAdapter(this, catalogueMode);
		RecyclerView speciesList = findViewById(R.id.speciesList);
		speciesList.setAdapter(speciesCatalogueAdapter);
		speciesList.setLayoutManager(new LinearLayoutManager(this));
		
		EditText searchCriterionEditText = findViewById(R.id.searchCriterionEditText);
		
		if (catalogueMode == CATALOGUE_MODE_SEARCH) {
			searchCriterionEditText.addTextChangedListener(new TextWatcher() {
				@Override
				public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
					speciesCatalogueAdapter.updateCursor(charSequence.toString());
					speciesCatalogueAdapter.notifyDataSetChanged();
				}
				
				@Override
				public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
				}
				
				@Override
				public void afterTextChanged(Editable editable) {
				}
			});
		} else {
			searchCriterionEditText.setVisibility(View.GONE);
		}
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if (catalogueMode == CATALOGUE_MODE_TOUR)
			requestUserLocation();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		if (catalogueMode == CATALOGUE_MODE_TOUR && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
			locationProviderClient.removeLocationUpdates(locationCallback);
	}
	
	
	@Override
	public void onSpeciesClick(int position) {
		int selectedSpeciesId = speciesCatalogueAdapter.getSelectedSpecies(position).getId();
		Intent intent = new Intent(MainApplication.getContext(), SpeciesActivity.class);
		intent.putExtra(MainApplication.INTENT_EXTRA_SPECIES_ID, selectedSpeciesId);
		intent.putIntegerArrayListExtra(MainApplication.INTENT_EXTRA_SPECIES_VIEWPAGER_FILTERED_LIST, speciesCatalogueAdapter.getFilteredSpeciesList());
		startActivity(intent);
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
					speciesCatalogueAdapter.updateCursor(userLocation);
					speciesCatalogueAdapter.notifyDataSetChanged();
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