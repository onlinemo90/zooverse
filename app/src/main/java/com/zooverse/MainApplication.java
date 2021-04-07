package com.zooverse;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.zooverse.zoo.Zoo;
import com.zooverse.notifications.TicketNotificationHandler;

public class MainApplication extends Application {
	private static Context appContext;
	private static SharedPreferences appPreferences;
	
	// Permission requests
	public static final int PERMISSION_REQUEST_CAMERA = 1;
	public static final int PERMISSION_REQUEST_LOCATION = 2;
	
	@Override
	public void onCreate() {
		super.onCreate();
		appContext = this;
		appPreferences = PreferenceManager.getDefaultSharedPreferences(appContext);
		
		AssetManager.init();
		Zoo.init();
		Theme.init();
		TicketNotificationHandler.createNotificationChannel();
	}
	
	public static Context getContext() {
		return appContext;
	}
	
	public static SharedPreferences getSharedPreferences() {
		return appPreferences;
	}
}


