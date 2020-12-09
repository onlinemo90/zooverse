package com.zooverse.model.database;

import android.provider.BaseColumns;

public final class DatabaseContract {
	// Note: all table and column names must be in uppercase
	public static final class TicketEntry implements BaseColumns {
		public static final String TABLE_NAME = "TICKET";
		
		public static final String COLUMN_ZOO_ID = "zoo_id";
		public static final String COLUMN_DATE = "date";
		
		public static final String DATE_FORMAT = "yyyyMMdd";
	}
	
	public static final class SpeciesEntry implements BaseColumns {
		public static final String TABLE_NAME = "SPECIES";
		
		public static final String COLUMN_NAME = "name";
		public static final String COLUMN_IMAGE = "image";
		public static final String COLUMN_DESCRIPTION = "description";
		public static final String COLUMN_AUDIO = "audio";
	}
	
	public static final class IndividualEntry implements BaseColumns {
		public static final String TABLE_NAME = "INDIVIDUAL";
		
		public static final String COLUMN_SPECIES_ID = "species_id";
		public static final String COLUMN_NAME = "name";
		public static final String COLUMN_DOB = "dob";
		public static final String COLUMN_PLACE_OF_BIRTH = "place_of_birth";
		public static final String COLUMN_GENDER = "gender";
		public static final String COLUMN_WEIGHT = "weight";
		public static final String COLUMN_SIZE = "size";
		public static final String COLUMN_IMAGE = "image";
		
		public static final String DOB_FORMAT = "yyyyMMdd";
	}
	
	public static final class AnimalAttributeCategoryEntry implements BaseColumns {
		public static final String TABLE_NAME = "ANIMAL_ATTRIBUTE_CATEGORY";
		
		public static final String COLUMN_NAME = "name";
	}
	
	public static final class IndividualInfoEntry implements BaseColumns{
		public static final String TABLE_NAME = "INDIVIDUALS_ATTRIBUTES";
		
		public static final String COLUMN_INDIVIDUAL_ID = "individual_id";
		public static final String COLUMN_CATEGORY_ID = "category_id";
		public static final String COLUMN_FACT = "attribute";
	}
}
