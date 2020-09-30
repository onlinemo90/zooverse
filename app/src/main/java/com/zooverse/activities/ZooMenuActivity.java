package com.zooverse.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

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
		Log.d("zoo menu", "'Search for an Animal' button pressed");
	}
	
	public void scanEnclosure(View view){
		Log.d("zoo menu", "'Go on a Tour' button pressed");
	}
}