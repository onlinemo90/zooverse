package com.zooverse.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.zooverse.MainApplication;
import com.zooverse.R;
import com.zooverse.Servlet;
import com.zooverse.Theme;
import com.zooverse.model.Model;
import com.zooverse.model.Species;
import com.zooverse.model.Ticket;

import com.zooverse.notifications.TicketNotificationHandler;

public abstract class AbstractBaseActivity extends AppCompatActivity {
	private int themeResourceId;
	private boolean isOptionsMenuEnabled = false;
	protected int menuBarId = R.menu.menu_bar_main;
	
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
					drawable.mutate().setColorFilter(Theme.getColor(R.attr.themeColorBackground), PorterDuff.Mode.SRC_ATOP);
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
