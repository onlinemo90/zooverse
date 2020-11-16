package com.zooverse;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.preference.PreferenceManager;

import com.zooverse.model.Model;

public class MainApplication extends Application {
	private static Context appContext;
	private static SharedPreferences appPreferences;
	private static int themeResourceId;
	
	public static final String INTENT_EXTRA_SPECIES = "species_index";
	
	@Override
	public void onCreate() {
		super.onCreate();
		appContext = this;
		appPreferences = PreferenceManager.getDefaultSharedPreferences(appContext);
		
		AssetManager.init();
		Model.init();
		updateTheme();
	}
	
	public static Context getContext() {
		return appContext;
	}
	
	public static int getThemeResourceId() {
		return themeResourceId;
	}
	
	public static void updateTheme() {
		String theme = appPreferences.getString(appContext.getResources().getString(R.string.theme_pref_key), appContext.getResources().getString(R.string.theme_default));
		if (theme.equals(appContext.getString(R.string.theme_light))) {
			themeResourceId = R.style.AppTheme_Light;
		} else if (theme.equals(appContext.getString(R.string.theme_dark))) {
			themeResourceId = R.style.AppTheme_Dark;
		} else {
			resetTheme();
		}
		appContext.setTheme(themeResourceId);
	}
	
	private static void resetTheme() {
		SharedPreferences.Editor sPEditor = appPreferences.edit();
		sPEditor.putString(appContext.getResources().getString(R.string.theme_pref_key), appContext.getResources().getString(R.string.theme_default));
		sPEditor.apply();
		updateTheme();
	}
	
	public static int getThemeColor(int attrResourceId) {
		TypedValue typedValue = new TypedValue();
		appContext.getTheme().resolveAttribute(attrResourceId, typedValue, true);
		return typedValue.data;
	}
	
	public static void setCardViewTheme(CardView...cards){
		for (CardView card : cards){
			card.setCardBackgroundColor(MainApplication.getThemeColor(R.attr.themeColorCardBackground));
			
			// check if ConstraintLayout is manages item within card view
			ViewGroup parent;
			if (card.getChildCount() > 0 && card.getChildAt(0) instanceof ConstraintLayout)
				parent = (ViewGroup) card.getChildAt(0);
			else
				parent = card;
			
			for (int i=0; i<parent.getChildCount();i++){
				View cardChild = parent.getChildAt(i);
				if (cardChild instanceof ImageView){
					((ImageView) cardChild).setColorFilter(MainApplication.getThemeColor(R.attr.themeColorForeground));
				}
			}
		}
	}
}


