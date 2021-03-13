package com.zooverse.activities;

import android.content.Intent;
import android.os.Bundle;
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
				(CardView) findViewById(R.id.cardViewAroundMe),
				(CardView) findViewById(R.id.cardViewScanInfoPoint),
				(CardView) findViewById(R.id.cardViewSearchSpecies),
				(CardView) findViewById(R.id.cardViewZooInfo)
		);
	}
	
	public void openAroundMe(View view) {
		startActivity(new Intent(MainApplication.getContext(), SubjectCatalogueLocationActivity.class));
	}
	
	public void openSpeciesSearch(View view) {
		startActivity(new Intent(MainApplication.getContext(), SubjectCatalogueSearchActivity.class));
	}
	
	public void openInfoPointScan(View view) {
		startActivity(new Intent(MainApplication.getContext(), QRCodeReaderActivity.class));
	}
	
	public void openZooInformation(View view) {
		startActivity(new Intent(MainApplication.getContext(), ZooInformationActivity.class));
	}
}