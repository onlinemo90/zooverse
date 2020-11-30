package com.zooverse.utils;

import com.zooverse.AssetManager;
import com.zooverse.BuildConfig;
import com.zooverse.MainApplication;
import com.zooverse.R;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.RhinoException;
import org.mozilla.javascript.Scriptable;

public class EncryptionHelper {
	private static final String ENCRYPTION_KEY = MainApplication.getContext().getString(R.string.encryption_key);
	
	// Rhino initialisation - http://old.brionmario.com/mozilla-rhino-javascript-engine/
	private static final Context RHINO_CONTEXT = org.mozilla.javascript.Context.enter();
	private static final Scriptable RHINO_SCOPE = RHINO_CONTEXT.initStandardObjects();
	
	private static final Function ENCRYPTION_FUNCTION = getEncryptionFunction("encrypt");
	private static final Function DECRYPTION_FUNCTION = getEncryptionFunction("decrypt");
	
	public static String encrypt(String plainText) {
		return runEncryptionFunction(ENCRYPTION_FUNCTION, plainText);
	}
	
	public static String decrypt(String cipherText) {
		// Encryption disabled for Application in development but never for released versions
		if (BuildConfig.DEBUG)
			return cipherText;
		else
			return runEncryptionFunction(DECRYPTION_FUNCTION, cipherText);
	}
	
	private EncryptionHelper() {
		// Prevent class initialisation
	}
	
	private static Function getEncryptionFunction(String functionName) {
		// Use Rhino to compile Javascript script
		RHINO_CONTEXT.setOptimizationLevel(-1); // disabling the optimizer to better support Android
		try {
			RHINO_CONTEXT.evaluateString(RHINO_SCOPE, AssetManager.ENCRYPTION_SCRIPT, "JavaScript", 1, null);
			return (Function) RHINO_SCOPE.get(functionName, RHINO_SCOPE);
		} catch (RhinoException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static String runEncryptionFunction(Function encryptionFunction, String inputText) {
		Object[] functionParams = new Object[]{inputText, ENCRYPTION_KEY, 256};
		return (String) encryptionFunction.call(RHINO_CONTEXT, RHINO_SCOPE, RHINO_SCOPE, functionParams);
	}
}
