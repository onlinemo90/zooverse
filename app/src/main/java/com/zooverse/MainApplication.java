package com.zooverse;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.zooverse.model.Model;

public class MainApplication extends Application {
	private static Context appContext;
	private static SharedPreferences appPreferences;
	
	//Intent Extras
	public static final String INTENT_EXTRA_SPECIES_ID = "species_id";
	public static final String INTENT_EXTRA_SPECIES_CATALOGUE_MODE = "species_catalogue_mode";
	public static final String INTENT_EXTRA_SPECIES_VIEWPAGER_FILTERED_LIST = "species_filtered_list";
	
	//Permission requests
	public static final int PERMISSION_REQUEST_CAMERA = 1;
	public static final int PERMISSION_REQUEST_LOCATION = 2;
	
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


