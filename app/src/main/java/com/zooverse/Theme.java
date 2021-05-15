package com.zooverse;

import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

public class Theme {
	public static final String THEME_COLOR_DEFAULT = "THEME_COLOR_DEFAULT";
	public static final String THEME_COLOR_ACTIVE = "THEME_COLOR_ACTIVE";
	public static final String THEME_COLOR_DISABLED = "THEME_COLOR_DISABLED";
	public static final String THEME_COLOR_BACKGROUND = "THEME_COLOR_BACKGROUND";
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
		SharedPreferences.Editor spEditor = MainApplication.getSharedPreferences().edit();
		spEditor.putString(MainApplication.getContext().getResources().getString(R.string.theme_pref_key), MainApplication.getContext().getResources().getString(R.string.theme_default));
		spEditor.apply();
		init();
	}
	
	public static int getResourceId() {
		return resourceId;
	}
	
	private static int getColor(int attrResourceId) {
		TypedValue typedValue = new TypedValue();
		MainApplication.getContext().getTheme().resolveAttribute(attrResourceId, typedValue, true);
		return typedValue.data;
	}
	
	public static void apply (String themeMode, CardView... cards) {
		for (CardView card : cards) {
			switch (themeMode) {
				case THEME_COLOR_DEFAULT:
					card.setCardBackgroundColor(getColor(R.attr.themeColorCardBackground));
					break;
				case THEME_COLOR_ACTIVE:
					card.setCardBackgroundColor(getColor(R.attr.themeColorPrimary));
					break;
				case THEME_COLOR_DISABLED:
					card.setCardBackgroundColor(getColor(R.attr.themeColorForegroundFaded));
					break;
				default:
					return;
			}
			
			// check if ConstraintLayout manages item within card view
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
	
	public static void apply (String themeMode, ImageView... imageViews) {
		for (ImageView imageView : imageViews){
			switch (themeMode) {
				case THEME_COLOR_DEFAULT:
					imageView.setColorFilter(getColor(R.attr.themeColorForeground), PorterDuff.Mode.SRC_ATOP);
					break;
				case THEME_COLOR_ACTIVE:
					imageView.setColorFilter(getColor(R.attr.themeColorPrimary), PorterDuff.Mode.SRC_ATOP);
					break;
				case THEME_COLOR_DISABLED:
					imageView.setColorFilter(getColor(R.attr.themeColorForegroundFaded), PorterDuff.Mode.SRC_ATOP);
					break;
				case THEME_COLOR_BACKGROUND:
					imageView.setColorFilter(getColor(R.attr.themeColorBackground), PorterDuff.Mode.SRC_ATOP);
					break;
				default:
					return;
			}
		}
	}
	
	public static void apply (String themeMode, TextView... textViews) {
		for (TextView textView : textViews){
			switch (themeMode) {
				case THEME_COLOR_DEFAULT:
					textView.setTextColor(getColor(R.attr.themeColorForeground));
					break;
				case THEME_COLOR_ACTIVE:
					textView.setTextColor(getColor(R.attr.themeColorPrimary));
					break;
				case THEME_COLOR_DISABLED:
					textView.setTextColor(getColor(R.attr.themeColorForegroundFaded));
					break;
				case THEME_COLOR_BACKGROUND:
					textView.setTextColor(getColor(R.attr.themeColorBackground));
					break;
				default:
					return;
			}
		}
	}
	
	public static void apply (String themeMode, Drawable... drawables){
		for (Drawable drawable : drawables) {
			switch (themeMode) {
				case THEME_COLOR_DEFAULT:
					drawable.setColorFilter(getColor(R.attr.themeColorForeground), PorterDuff.Mode.SRC_ATOP);
					break;
				case THEME_COLOR_ACTIVE:
					drawable.setColorFilter(getColor(R.attr.themeColorPrimary), PorterDuff.Mode.SRC_ATOP);
					break;
				case THEME_COLOR_DISABLED:
					drawable.setColorFilter(getColor(R.attr.themeColorForegroundFaded), PorterDuff.Mode.SRC_ATOP);
					break;
				case THEME_COLOR_BACKGROUND:
					drawable.setColorFilter(getColor(R.attr.themeColorBackground), PorterDuff.Mode.SRC_ATOP);
					break;
				default:
					return;
			}
		}
	}
}
