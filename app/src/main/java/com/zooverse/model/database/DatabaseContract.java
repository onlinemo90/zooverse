package com.zooverse.model.database;

import android.provider.BaseColumns;

public final class DatabaseContract {
	// Note: all table and column names must be in uppercase
	public static final class TicketEntry implements BaseColumns{
		public static final String TABLE_NAME = "TICKET";
		
		public static final String COLUMN_ZOO_ID = "ZOO_ID";
		public static final String COLUMN_DATE = "DATE";
	}
	
	public static final class SpeciesEntry implements BaseColumns{
		public static final String TABLE_NAME = "SPECIES";
		
		public static final String COLUMN_NAME = "NAME";
		public static final String COLUMN_IMAGE = "IMAGE";
		public static final String COLUMN_DESCRIPTION = "DESCRIPTION";
		public static final String COLUMN_AUDIO = "AUDIO";
	}
}
