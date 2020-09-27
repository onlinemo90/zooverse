package com.zooverse;

import java.io.IOException;
import java.io.InputStream;

public class AssetManager {
	public static final String ENCRYPTION_SCRIPT = getEncryptionScript();
	public static final String DATABASE_NAME = "zooverseDB.db";
	
	private static final String ENCRYPTION_FILE = "aes.js";
	
	private AssetManager(){
		// Prevent initialisation
	}
	
	public static void init(){
		// Empty method to initialise constants
	}
	
	private static String getEncryptionScript(){
		// Read JavaScript logic from file
		try {
			InputStream externalScript = MainApplication.getContext().getAssets().open(ENCRYPTION_FILE);
			byte[] bytes = new byte[externalScript.available()];
			externalScript.read(bytes);
			externalScript.close();
			return new String(bytes);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
