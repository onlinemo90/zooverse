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
	
	public static void applyDefault(ImageView... imageViews){
		apply(R.attr.themeColorForeground, imageViews);
	}
	
	public static void applyDefault(CardView... cards) {
		apply(R.attr.themeColorCardBackground, cards);
	}
	
	public static void applyActive(TextView... textViews){
		apply(R.attr.themeColorPrimary, textViews);
	}
	
	public static void applyActive(ImageView... imageViews){
		apply(R.attr.themeColorPrimary, imageViews);
	}
	
	public static void applyDisabled(ImageView... imageViews){
		apply(R.attr.themeColorForegroundFaded, imageViews);
	}
	
	public static void applyActive(CardView... cards) {
		apply(R.attr.themeColorPrimary, cards);
	}
	
	public static void applyBackground(TextView... textViews){
		apply(R.attr.themeColorBackground, textViews);
	}
	
	public static void applyBackground(ImageView... imageViews){
		apply(R.attr.themeColorBackground, imageViews);
	}
	
	public static void applyBackground(Drawable... drawables){
		for (Drawable drawable : drawables) {
			drawable.setColorFilter(getColor(R.attr.themeColorBackground), PorterDuff.Mode.SRC_ATOP);
		}
	}
	
	private static int getColor(int attrResourceId) {
		TypedValue typedValue = new TypedValue();
		MainApplication.getContext().getTheme().resolveAttribute(attrResourceId, typedValue, true);
		return typedValue.data;
	}
	
	private static void apply(int colorResId, CardView... cards){
		for (CardView card : cards) {
			card.setCardBackgroundColor(getColor(colorResId));
			
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
	
	private static void apply(int colorResId, ImageView... imageViews){
		for (ImageView imageView : imageViews){
			imageView.setColorFilter(getColor(colorResId), PorterDuff.Mode.SRC_ATOP);
		}
	}
	
	private static void apply(int colorResId, TextView... textViews){
		for (TextView textView : textViews){
			textView.setTextColor(getColor(colorResId));
		}
	}
}
