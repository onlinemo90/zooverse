package com.zooverse.activities;


import android.content.Intent;
import android.widget.Toast;

import com.zooverse.MainApplication;
import com.zooverse.R;
import com.zooverse.model.Model;
import com.zooverse.model.Species;

public class ScanInfoPointActivity extends AbstractQRCodeReaderActivity {
	
	@Override
	public void processQRCode(String qrContent) {
		int speciesListIndex = -1;
		for (Species scannedSpecies : Model.getSpeciesList()) {
			speciesListIndex++;
			if (qrContent.equals(Integer.toString(scannedSpecies.getId()))) {
				Intent intent = new Intent(MainApplication.getContext(), SpeciesActivity.class);
				intent.putExtra(MainApplication.INTENT_EXTRA_SPECIES, speciesListIndex);
				startActivity(intent);
				return;
			}
		}
		Toast.makeText(MainApplication.getContext(), R.string.scan_infopoint_error_invalid_qr, Toast.LENGTH_SHORT).show();
	}
}