package com.zooverse.activities;

import androidx.annotation.NonNull;
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
import com.zooverse.zoo.Subject;
import com.zooverse.zoo.Zoo;

import java.util.Collections;


public class SubjectLocationActivity extends FragmentActivity implements OnMapReadyCallback {
	public enum IntentExtras {
		SUBJECT_UUID
	}
	
	private GoogleMap zooMap;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_subject_location);
		
		// Obtain the SupportMapFragment and get notified when the map is ready to be used.
		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
		mapFragment.getMapAsync(this);
	}
	
	@Override
	public void onMapReady(GoogleMap googleMap) {
		zooMap = googleMap;
		Subject subject = Zoo.getSubjectList(Collections.singletonList(getIntent().getStringExtra(IntentExtras.SUBJECT_UUID.toString()))).get(0);
		
		Bitmap speciesBitmap = subject.getImage();
		LatLng speciesCoordinates = new LatLng(subject.getLocation().first, subject.getLocation().second);
		MarkerOptions markerOptions = new MarkerOptions();
		markerOptions.position(speciesCoordinates);
		markerOptions.title(subject.getName());
		markerOptions.icon(BitmapDescriptorFactory.fromBitmap(buildMapsPin(speciesBitmap)));
		
		zooMap.addMarker(markerOptions);
		zooMap.moveCamera(CameraUpdateFactory.newLatLngZoom(speciesCoordinates, this.getResources().getInteger(R.integer.initial_zoom_out)));
		zooMap.getUiSettings().setMyLocationButtonEnabled(true);
		zooMap.getUiSettings().setZoomControlsEnabled(true);
		zooMap.getUiSettings().setCompassEnabled(true);
		zooMap.setMinZoomPreference(this.getResources().getInteger(R.integer.boundary_zoom_out));
		
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
			//Location Permission already granted
			zooMap.setMyLocationEnabled(true);
			
		} else {
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MainApplication.PERMISSION_REQUEST_LOCATION);
		}
	}
	
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		if (requestCode == MainApplication.PERMISSION_REQUEST_LOCATION) {
			// If request is cancelled, the result arrays are empty.
			if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
					// Permission granted
					zooMap.setMyLocationEnabled(true);
			} else
				// permission denied
				Toast.makeText(this, getString(R.string.location_on_map_permission_denied), Toast.LENGTH_LONG).show();
		}
	}
	
	public Bitmap buildMapsPin(Bitmap speciesBitmap) {
		Bitmap pinBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_pin);
		pinBitmap = Bitmap.createScaledBitmap(pinBitmap, this.getResources().getInteger(R.integer.bitmap_dimensions_pin), this.getResources().getInteger(R.integer.bitmap_dimensions_pin), true);
		
		if (speciesBitmap.getWidth() >= speciesBitmap.getHeight()) {
			speciesBitmap = Bitmap.createBitmap(speciesBitmap, speciesBitmap.getWidth() / 2 - speciesBitmap.getHeight() / 2, 0, speciesBitmap.getHeight(), speciesBitmap.getHeight());
		} else {
			speciesBitmap = Bitmap.createBitmap(speciesBitmap, 0, speciesBitmap.getHeight() / 2 - speciesBitmap.getWidth() / 2, speciesBitmap.getWidth(), speciesBitmap.getWidth());
		}
		speciesBitmap = Bitmap.createScaledBitmap(speciesBitmap, this.getResources().getInteger(R.integer.bitmap_dimensions_species), this.getResources().getInteger(R.integer.bitmap_dimensions_species), true);
		
		Bitmap mapsPin = Bitmap.createBitmap(pinBitmap.getWidth(), pinBitmap.getHeight(), pinBitmap.getConfig());
		Canvas canvas = new Canvas(mapsPin);
		float paddingLeft = (pinBitmap.getWidth() - speciesBitmap.getWidth()) / 2;
		float paddingTop = (pinBitmap.getWidth() - speciesBitmap.getWidth()) / 4;
		canvas.drawBitmap(pinBitmap, 0f, 0f, null);
		canvas.drawBitmap(speciesBitmap, paddingLeft, paddingTop, null);
		return mapsPin;
	}
}