package com.zooverse.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.cardview.widget.CardView;

import com.zooverse.MainApplication;
import com.zooverse.R;
import com.zooverse.Theme;

public class ZooMenuActivity extends AbstractBaseActivity {
	
	@Override
	@SuppressLint("MissingSuperCall")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.activity_zoo_menu, true);
		
		Theme.applyDefault(
				(CardView) findViewById(R.id.cardViewAroundMe),
				findViewById(R.id.cardViewScanInfoPoint),
				findViewById(R.id.cardViewSearchSpecies),
				findViewById(R.id.cardViewZooInfo)
		);
	}
	
	public void openAroundMe(View view) {
		startActivity(new Intent(MainApplication.getContext(), AroundMeActivity.class));
	}
	
	public void openSpeciesSearch(View view) {
		startActivity(new Intent(MainApplication.getContext(), SubjectSearchActivity.class));
	}
	
	public void openInfoPointScan(View view) {
		startActivity(new Intent(MainApplication.getContext(), QRCodeReaderActivity.class));
	}
	
	public void openZooInformation(View view) {
		startActivity(new Intent(MainApplication.getContext(), ZooInformationActivity.class));
	}
}