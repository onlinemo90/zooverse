package com.zooverse;

import android.content.SharedPreferences;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

public class Theme {
	private static int resourceId;
	
	private Theme() {
		// prevent initialisation
	}
	
	public static void init() {
		String theme = MainApplication.getSharedPreferences().getString(MainApplication.getContext().getResources().getString(R.string.theme_pref_key), MainApplication.getContext().getResources().getString(R.string.theme_default));
		if (theme.equals(MainApplication.getContext().getString(R.string.theme_light))) {
			resourceId = R.style.AppTheme_Light;
		} else if (theme.equals(MainApplication.getContext().getString(R.string.theme_dark))) {
			resourceId = R.style.AppTheme_Dark;
		} else {
			reset();
		}
		MainApplication.getContext().setTheme(resourceId);
	}
	
	public static void reset() {
		SharedPreferences.Editor sPEditor = MainApplication.getSharedPreferences().edit();
		sPEditor.putString(MainApplication.getContext().getResources().getString(R.string.theme_pref_key), MainApplication.getContext().getResources().getString(R.string.theme_default));
		sPEditor.apply();
		init();
	}
	
	public static int getResourceId() {
		return resourceId;
	}
	
	public static int getColor(int attrResourceId) {
		TypedValue typedValue = new TypedValue();
		MainApplication.getContext().getTheme().resolveAttribute(attrResourceId, typedValue, true);
		return typedValue.data;
	}
	
	public static void apply(CardView... cards) {
		for (CardView card : cards) {
			card.setCardBackgroundColor(getColor(R.attr.themeColorCardBackground));
			
			// check if ConstraintLayout is manages item within card view
			ViewGroup parent;
			if (card.getChildCount() > 0 && card.getChildAt(0) instanceof ConstraintLayout)
				parent = (ViewGroup) card.getChildAt(0);
			else
				parent = card;
			
			for (int i = 0; i < parent.getChildCount(); i++) {
				View cardChild = parent.getChildAt(i);
				if (cardChild instanceof ImageView) {
					((ImageView) cardChild).setColorFilter(getColor(R.attr.themeColorForeground));
				}
			}
		}
	}
	public static void apply(ImageView... images) {
		for (ImageView image : images){
			image.setColorFilter(getColor(R.attr.themeColorForeground));
		}
	}
}
