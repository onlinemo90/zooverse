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
		Intent intent = new Intent(MainApplication.getContext(), SpeciesCatalogueActivity.class);
		intent.putExtra(MainApplication.INTENT_EXTRA_SPECIES_CATALOGUE_MODE, SpeciesCatalogueActivity.CATALOGUE_MODE_TOUR);
		startActivity(intent);
	}
	
	public void openSpeciesSearch(View view) {
		Intent intent = new Intent(MainApplication.getContext(), SpeciesCatalogueActivity.class);
		intent.putExtra(MainApplication.INTENT_EXTRA_SPECIES_CATALOGUE_MODE, SpeciesCatalogueActivity.CATALOGUE_MODE_SEARCH);
		startActivity(intent);
	}
	
	public void openInfoPointScan(View view) {
		startActivity(new Intent(MainApplication.getContext(), QRCodeReaderActivity.class));
	}
	
	public void openZooInformation(View view) {
		startActivity(new Intent(MainApplication.getContext(), ZooInformationActivity.class));
	}
}