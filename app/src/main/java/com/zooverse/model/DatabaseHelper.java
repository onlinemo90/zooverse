package com.zooverse.model;

import com.zooverse.AssetManager;
import com.zooverse.MainApplication;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseHelper extends SQLiteAssetHelper {
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper() {
        super(MainApplication.getContext(), AssetManager.DATABASE_NAME, null, DATABASE_VERSION);
    }
}
