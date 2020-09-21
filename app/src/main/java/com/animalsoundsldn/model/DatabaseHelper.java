package com.animalsoundsldn.model;

import com.animalsoundsldn.MainApplication;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseHelper extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "animalsoundsdbmain.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper() {
        super(MainApplication.getContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }
}
