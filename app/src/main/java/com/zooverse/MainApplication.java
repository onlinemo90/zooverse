package com.zooverse;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class MainApplication extends Application {
	private static Context appContext;
	private static SharedPreferences appPreferences;
	
	@Override
	public void onCreate() {
		super.onCreate();
		appContext = this;
		AssetManager.init();
		
		appPreferences = getSharedPreferences("appPreferences", Context.MODE_PRIVATE);
	}
	
	public static Context getContext() {
		return appContext;
	}
	
	public static void setLastTicketPreference(String ticketContent){
		SharedPreferences.Editor preferenceEditor = appPreferences.edit();
		preferenceEditor.putString("scannedTicket", ticketContent);
		preferenceEditor.apply();
	}
	public static String getLastTicketPreference(){
		return appPreferences.getString("scannedTicket","");
	}
}


