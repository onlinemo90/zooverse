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
	
	private static int resourceId;
	
	public enum Mode {
		DEFAULT,
		ACTIVE,
		DISABLED,
		BACKGROUND
	}
	
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
	
	public static void apply (Mode themeMode, CardView... cards) {
		for (CardView card : cards) {
			switch (themeMode) {
				case DEFAULT:
					card.setCardBackgroundColor(getColor(R.attr.themeColorCardBackground));
					break;
				case ACTIVE:
					card.setCardBackgroundColor(getColor(R.attr.themeColorPrimary));
					break;
				case DISABLED:
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
	
	public static void apply (Mode themeMode, ImageView... imageViews) {
		for (ImageView imageView : imageViews){
			switch (themeMode) {
				case DEFAULT:
					imageView.setColorFilter(getColor(R.attr.themeColorForeground), PorterDuff.Mode.SRC_ATOP);
					break;
				case ACTIVE:
					imageView.setColorFilter(getColor(R.attr.themeColorPrimary), PorterDuff.Mode.SRC_ATOP);
					break;
				case DISABLED:
					imageView.setColorFilter(getColor(R.attr.themeColorForegroundFaded), PorterDuff.Mode.SRC_ATOP);
					break;
				case BACKGROUND:
					imageView.setColorFilter(getColor(R.attr.themeColorBackground), PorterDuff.Mode.SRC_ATOP);
					break;
				default:
					return;
			}
		}
	}
	
	public static void apply (Mode themeMode, TextView... textViews) {
		for (TextView textView : textViews){
			switch (themeMode) {
				case DEFAULT:
					textView.setTextColor(getColor(R.attr.themeColorForeground));
					break;
				case ACTIVE:
					textView.setTextColor(getColor(R.attr.themeColorPrimary));
					break;
				case DISABLED:
					textView.setTextColor(getColor(R.attr.themeColorForegroundFaded));
					break;
				case BACKGROUND:
					textView.setTextColor(getColor(R.attr.themeColorBackground));
					break;
				default:
					return;
			}
		}
	}
	
	public static void apply (Mode themeMode, Drawable... drawables){
		for (Drawable drawable : drawables) {
			switch (themeMode) {
				case DEFAULT:
					drawable.setColorFilter(getColor(R.attr.themeColorForeground), PorterDuff.Mode.SRC_ATOP);
					break;
				case ACTIVE:
					drawable.setColorFilter(getColor(R.attr.themeColorPrimary), PorterDuff.Mode.SRC_ATOP);
					break;
				case DISABLED:
					drawable.setColorFilter(getColor(R.attr.themeColorForegroundFaded), PorterDuff.Mode.SRC_ATOP);
					break;
				case BACKGROUND:
					drawable.setColorFilter(getColor(R.attr.themeColorBackground), PorterDuff.Mode.SRC_ATOP);
					break;
				default:
					return;
			}
		}
	}
}
