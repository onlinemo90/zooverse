package com.zooverse.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.cardview.widget.CardView;

import com.zooverse.MainApplication;
import com.zooverse.R;

public class ZooMenuActivity extends AbstractBaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zoo_menu);
		CardView cardViewTour = findViewById(R.id.cardViewTour);
		CardView cardViewScanInfoPoint = findViewById(R.id.cardViewScanInfoPoint);
		CardView cardViewSearchSpecies = findViewById(R.id.cardViewSearchSpecies);
		CardView cardViewZooInfo = findViewById(R.id.cardViewZooInfo);
		
		MainApplication.setCardViewTheme(
				cardViewTour,
				cardViewScanInfoPoint,
				cardViewSearchSpecies,
				cardViewZooInfo);
	}
	
	public void openTour(View view) {
		Log.d("zoo menu", "'Go on a Tour' button pressed");
	}
	
	public void openZooInformation(View view) { Log.d("zoo menu", "'Zoo Information' button pressed"); }
	
	public void openSpeciesSearch(View view) {
		startActivity(new Intent(MainApplication.getContext(), SpeciesSearchActivity.class));
	}
	
	public void openInfoPointScan(View view) {
		startActivity(new Intent(MainApplication.getContext(), ScanInfoPointActivity.class));
	}
}