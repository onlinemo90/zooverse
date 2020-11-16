package com.zooverse.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.zooverse.Theme;

public abstract class AbstractBaseActivity extends AppCompatActivity {
	private int themeResourceId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.themeResourceId = Theme.getResourceId();
		setTheme(this.themeResourceId);
		super.onCreate(savedInstanceState);
	}
	
	
	@Override
	protected void onResume() {
		super.onResume();
		if (themeResourceId != Theme.getResourceId()) {
			recreate();
		}
	}
}
