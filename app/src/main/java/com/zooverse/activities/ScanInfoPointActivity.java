package com.zooverse.activities;


import android.content.Intent;
import android.widget.Toast;

import com.zooverse.MainApplication;
import com.zooverse.R;
import com.zooverse.model.Model;

public class ScanInfoPointActivity extends AbstractQRCodeReaderActivity {
	
	@Override
	public void processQRCode(String qrContent) {
		Integer speciesId = Integer.parseInt(qrContent);
		if (Model.getSpecies().containsKey(speciesId)) {
			Intent intent = new Intent(MainApplication.getContext(), SpeciesActivity.class);
			intent.putExtra(MainApplication.INTENT_EXTRA_SPECIES_ID, speciesId);
			startActivity(intent);
		} else
			Toast.makeText(MainApplication.getContext(), R.string.scan_infopoint_error_invalid_qr, Toast.LENGTH_SHORT).show();
	}
}