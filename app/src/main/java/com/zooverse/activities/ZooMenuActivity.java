package com.zooverse.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.cardview.widget.CardView;

import com.zooverse.MainApplication;
import com.zooverse.R;
import com.zooverse.Theme;

public class ZooMenuActivity extends AbstractBaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zoo_menu);
		this.enableOptionsMenu();
		
		Theme.apply(
				(CardView) findViewById(R.id.cardViewTour),
				(CardView) findViewById(R.id.cardViewScanInfoPoint),
				(CardView) findViewById(R.id.cardViewSearchSpecies),
				(CardView) findViewById(R.id.cardViewZooInfo)
		);
	}
	
	public void openTour(View view) {
		Log.d("zoo menu", "'Go on a Tour' button pressed");
	}
	
	public void openZooInformation(View view) {
		startActivity(new Intent(MainApplication.getContext(), ZooInformationActivity.class));
	}
	
	public void openSpeciesSearch(View view) {
		startActivity(new Intent(MainApplication.getContext(), SpeciesSearchActivity.class));
	}
	
	public void openInfoPointScan(View view) {
		startActivity(new Intent(MainApplication.getContext(), QRCodeReaderActivity.class));
	}
}