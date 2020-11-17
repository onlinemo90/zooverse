package com.zooverse;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.zooverse.model.Model;

public class MainApplication extends Application {
	private static Context appContext;
	private static SharedPreferences appPreferences;
	
	public static final String INTENT_EXTRA_SPECIES_ID = "species_index";
	
	@Override
	public void onCreate() {
		super.onCreate();
		appContext = this;
		appPreferences = PreferenceManager.getDefaultSharedPreferences(appContext);
		
		AssetManager.init();
		Model.init();
		Theme.init();
	}
	
	public static Context getContext() {
		return appContext;
	}
	
	public static SharedPreferences getSharedPreferences() {
		return appPreferences;
	}
}


