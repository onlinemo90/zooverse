package com.zooverse.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.zooverse.MainApplication;
import com.zooverse.R;
import com.zooverse.Servlet;
import com.zooverse.Theme;

public abstract class AbstractBaseActivity extends AppCompatActivity {
	private int themeResourceId;
	private boolean isOptionsMenuEnabled = false;
	protected int menuBarId = R.menu.menu_bar_main;
	
	protected void onCreateCustom(Bundle savedInstanceState) {
		this.themeResourceId = Theme.getResourceId();
		setTheme(this.themeResourceId);
		super.onCreate(savedInstanceState);
	}
	
	protected void onCreate(Bundle savedInstanceState, int layoutResId, boolean enableOptionsMenu){
		onCreateCustom(savedInstanceState);
		setContentView(layoutResId);
		this.isOptionsMenuEnabled = enableOptionsMenu;
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if (themeResourceId != Theme.getResourceId()) {
			recreate();
		}
	}
	
	protected void enableOptionsMenu() {
		this.isOptionsMenuEnabled = true;
	}
	
	protected void enableOptionsMenu(int menuBarId) {
		this.menuBarId = menuBarId;
		this.isOptionsMenuEnabled = true;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (this.isOptionsMenuEnabled) {
			getMenuInflater().inflate(menuBarId, menu);
			for (int i = 0; i < menu.size(); i++) {
				Drawable drawable = menu.getItem(i).getIcon();
				if (drawable != null) {
					Theme.applyBackground(drawable.mutate());
				}
			}
			return true;
		}
		return false;
	}
	
	@SuppressLint("NonConstantResourceId")
	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		switch (item.getItemId()) {
			case R.id.settingsMenuItem:
				startActivity(new Intent(MainApplication.getContext(), SettingsActivity.class));
				break;
			case R.id.scanTicketMenuItem:
				startActivity(new Intent(MainApplication.getContext(), QRCodeReaderActivity.class));
				break;
		}
		return true;
	}
	
	protected void processExternalRequest(String requestURL) {
		Servlet.process(requestURL, this);
	}
}
