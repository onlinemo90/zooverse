package com.zooverse.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.zooverse.MainApplication;
import com.zooverse.R;

public class ZooMenuActivity extends AbstractBaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zoo_menu);
	}
	
	public void openTour(View view) {
		Log.d("zoo menu", "'Go on a Tour' button pressed");
	}
	
	public void openSpeciesSearch(View view) {
		startActivity(new Intent(MainApplication.getContext(), SpeciesSearchActivity.class));
	}
	
	public void openInfoPointScan(View view) {
		startActivity(new Intent(MainApplication.getContext(), ScanInfoPointActivity.class));
	}
}