package com.animalsoundsldn;

import android.app.Application;
import android.content.Context;

public class MainApplication extends Application {
	private static Context appContext;
	
	@Override
	public void onCreate() {
		super.onCreate();
		appContext = this;
	}
	
	public static Context getContext() {
		return appContext;
	}
}
