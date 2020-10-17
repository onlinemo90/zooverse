package com.zooverse.activities;


import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.zooverse.MainApplication;
import com.zooverse.R;

public class SettingsActivity extends AbstractBaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.settings, new SettingsFragment())
				.commit();
	}
	
	public static class SettingsFragment extends PreferenceFragmentCompat {
		private SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener;
		
		
		@Override
		public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
			this.setPreferencesFromResource(R.xml.root_preferences, rootKey);
			preferenceChangeListener = (sharedPreferences, key) -> {
				if (MainApplication.getContext().getResources().getString(R.string.theme_pref_key).equals(key)) {
					MainApplication.updateTheme();
					requireActivity().recreate();
				}
			};
		}
		
		@Override
		public void onResume() {
			super.onResume();
			getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(preferenceChangeListener);
		}
		
		@Override
		public void onPause() {
			super.onPause();
			getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(preferenceChangeListener);
		}
	}
}