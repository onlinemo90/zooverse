package com.zooverse.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.zooverse.MainApplication;
import com.zooverse.R;

public class ZooMenuActivity extends AppCompatActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zoo_menu);
	}
	
	public void goOnTour(View view){
		Log.d("zoo menu", "'Go on a Tour' button pressed");
	}
	
	public void searchAnimal(View view){
		startActivity(new Intent(MainApplication.getContext(), SpeciesSearchActivity.class));
	}
	
	public void scanEnclosure(View view){
		Log.d("zoo menu", "'Scan enclosure' button pressed");
	}
}