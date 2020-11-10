package com.zooverse.model.database;

import android.provider.BaseColumns;

public final class DatabaseContract {
	// Note: all table and column names must be in uppercase
	public static final class TicketEntry implements BaseColumns {
		public static final String TABLE_NAME = "TICKET";
		
		public static final String COLUMN_ZOO_ID = "zoo_id";
		public static final String COLUMN_DATE = "date";
	}
	
	public static final class SpeciesEntry implements BaseColumns {
		public static final String TABLE_NAME = "SPECIES";
		
		public static final String COLUMN_NAME = "name";
		public static final String COLUMN_IMAGE = "image";
		public static final String COLUMN_DESCRIPTION = "description";
		public static final String COLUMN_AUDIO = "audio";
	}
}
