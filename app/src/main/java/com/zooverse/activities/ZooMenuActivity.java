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
				findViewById(R.id.cardViewTour),
				findViewById(R.id.cardViewScanInfoPoint),
				findViewById(R.id.cardViewSearchSpecies),
				findViewById(R.id.cardViewZooInfo)
		);
	}
	
	public void openTour(View view) {
		Log.d("zoo menu", "'Go on a Tour' button pressed");
	}
	
	public void openZooInformation(View view) {
		Log.d("zoo menu", "'Zoo Information' button pressed");
	}
	
	public void openSpeciesSearch(View view) {
		startActivity(new Intent(MainApplication.getContext(), SpeciesSearchActivity.class));
	}
	
	public void openInfoPointScan(View view) {
		startActivity(new Intent(MainApplication.getContext(), QRCodeReaderActivity.class));
	}
}