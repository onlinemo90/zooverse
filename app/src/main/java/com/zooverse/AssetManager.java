package com.zooverse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AssetManager {
	public static final String ENCRYPTION_SCRIPT = getAssetFileContents("aes.js");
	public static final String DATABASE_NAME = "zooverseDB.db";
	
	private AssetManager() {
		// Prevent initialisation
	}
	
	public static void init() {
		// Empty method to initialise constants
	}
	
	private static String getAssetFileContents(String filename) {
		try {
			InputStream fileStream = MainApplication.getContext().getAssets().open(filename);
			String fileLine;
			StringBuilder stringBuilder = new StringBuilder();
			BufferedReader reader = new BufferedReader(new InputStreamReader(fileStream));
			while ((fileLine = reader.readLine()) != null) {
				stringBuilder.append(fileLine).append(System.lineSeparator());
			}
			return stringBuilder.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
