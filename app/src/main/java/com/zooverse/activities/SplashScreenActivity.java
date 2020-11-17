package com.zooverse.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class SplashScreenActivity extends AbstractBaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			super.processExternalRequest(getIntent().getData().toString());
			Log.d("entryPoint", "Browser");
		} catch (NullPointerException npe) {
			Log.d("entryPoint", "Launcher");
		}
		startActivity(new Intent(this, MainActivity.class));
		finish();
	}
}
