package com.zooverse.activities;

import android.content.Intent;
import android.os.Bundle;

public class SplashScreenActivity extends AbstractBaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		startActivity(new Intent(this, MainActivity.class));
		finish();
	}
}
