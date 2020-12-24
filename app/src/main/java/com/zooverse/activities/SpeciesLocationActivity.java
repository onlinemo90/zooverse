package com.zooverse.activities;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.zooverse.MainApplication;
import com.zooverse.R;
import com.zooverse.model.Model;
import com.zooverse.model.Species;


public class SpeciesLocationActivity extends FragmentActivity implements OnMapReadyCallback {
	
	private GoogleMap zooMap;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_species_location);
		
		// Obtain the SupportMapFragment and get notified when the map is ready to be used.
		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
		mapFragment.getMapAsync(this);
	}
	
	@Override
	public void onMapReady(GoogleMap googleMap) {
		zooMap = googleMap;
		Species species = Model.getSpecies().get(getIntent().getIntExtra(MainApplication.INTENT_EXTRA_SPECIES_ID, 1));
		
		Bitmap speciesBitmap = species.getImage();
		LatLng speciesCoordinates = new LatLng(species.getLocation().first, species.getLocation().second);
		MarkerOptions markerOptions = new MarkerOptions();
		markerOptions.position(speciesCoordinates);
		markerOptions.title(species.getName());
		markerOptions.icon(BitmapDescriptorFactory.fromBitmap(buildMapsPin(speciesBitmap)));
		
		zooMap.addMarker(markerOptions);
		zooMap.moveCamera(CameraUpdateFactory.newLatLngZoom(speciesCoordinates,16));
		zooMap.getUiSettings().setMyLocationButtonEnabled(true);
		zooMap.getUiSettings().setZoomControlsEnabled(true);
		zooMap.getUiSettings().setCompassEnabled(true);
		zooMap.setMinZoomPreference(14);
		
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
			//Location Permission already granted
			zooMap.setMyLocationEnabled(true);
			
		}else {
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MainApplication.PERMISSION_REQUEST_LOCATION);
		}
	}
	
	@Override
	public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
		if (requestCode == MainApplication.PERMISSION_REQUEST_LOCATION) {
			// If request is cancelled, the result arrays are empty.
			if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
					// Permission granted
					zooMap.setMyLocationEnabled(true);
				else
					// permission denied
					Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
			}
		}
	}
	
	public Bitmap buildMapsPin (Bitmap speciesBitmap) {
		Bitmap pinBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_pin);
		pinBitmap = Bitmap.createScaledBitmap(pinBitmap, 256, 256, true);
		
		if (speciesBitmap.getWidth() >= speciesBitmap.getHeight()) {
			speciesBitmap = Bitmap.createBitmap(speciesBitmap, speciesBitmap.getWidth()/2 - speciesBitmap.getHeight()/2, 0, speciesBitmap.getHeight(), speciesBitmap.getHeight());
		} else {
			speciesBitmap = Bitmap.createBitmap(speciesBitmap, 0, speciesBitmap.getHeight()/2 - speciesBitmap.getWidth()/2, speciesBitmap.getWidth(), speciesBitmap.getWidth());
		}
		speciesBitmap = Bitmap.createScaledBitmap(speciesBitmap, 180, 180, true);
		
		Bitmap mapsPin = Bitmap.createBitmap(pinBitmap.getWidth(), pinBitmap.getHeight(), pinBitmap.getConfig());
		Canvas canvas = new Canvas(mapsPin);
		float paddingLeft = (pinBitmap.getWidth() - speciesBitmap.getWidth()) / 2;
		float paddingTop = (pinBitmap.getWidth() - speciesBitmap.getWidth()) / 4;
		canvas.drawBitmap(pinBitmap, 0f, 0f, null);
		canvas.drawBitmap(speciesBitmap, paddingLeft, paddingTop, null);
		return mapsPin;
	}
}