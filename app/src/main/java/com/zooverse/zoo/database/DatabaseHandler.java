package com.zooverse.zoo.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Pair;

import com.zooverse.AssetManager;
import com.zooverse.MainApplication;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import com.zooverse.zoo.Attribute;
import com.zooverse.zoo.Group;
import com.zooverse.zoo.Individual;
import com.zooverse.zoo.Species;
import com.zooverse.zoo.Subject;
import com.zooverse.zoo.Ticket;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseHandler extends SQLiteAssetHelper {
	private static final int DATABASE_VERSION = 86;
	private static DatabaseHandler instance = null;
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(DatabaseContract.DATE_FORMAT);
	
	private final SQLiteDatabase database;
	
	private DatabaseHandler() {
		super(MainApplication.getContext(), AssetManager.DATABASE_NAME, null, DATABASE_VERSION);
		this.setForcedUpgrade();
		
		this.database = getReadableDatabase();
	}
	
	public static DatabaseHandler getInstance() {
		if (instance == null) {
			instance = new DatabaseHandler();
		}
		return instance;
	}
	
	// Ticket-----------------------------------------------------------
	public List<Ticket> getStoredTickets(Date afterDate) {
		String afterDateString = dateFormat.format(afterDate);
		String[] columns = {
				DatabaseContract.TicketEntry.COLUMN_ZOO_ID,
				DatabaseContract.TicketEntry.COLUMN_DATE
		};
		String selection = DatabaseContract.TicketEntry.COLUMN_DATE + " >= ?";
		String[] selectionArgs = new String[]{afterDateString};
		Cursor cursor = database.query(
				DatabaseContract.TicketEntry.TABLE_NAME,
				columns,
				selection,
				selectionArgs,
				null,
				null,
				DatabaseContract.TicketEntry.COLUMN_DATE + " ASC"
		);
		
		// Parse data into List
		List<Ticket> ticketList = new ArrayList<>();
		String zooID;
		Date date;
		while (cursor.moveToNext()) {
			zooID = cursor.getString(cursor.getColumnIndex(DatabaseContract.TicketEntry.COLUMN_ZOO_ID));
			try {
				date = dateFormat.parse(cursor.getString(cursor.getColumnIndex(DatabaseContract.TicketEntry.COLUMN_DATE)));
			} catch (ParseException e) {
				continue;
			}
			ticketList.add(new Ticket(zooID, date));
		}
		cursor.close();
		return ticketList;
	}
	
	public boolean hasTodayTicket() {
		String todayDateStr = dateFormat.format(new Date());
		Cursor cursor = database.query(
				DatabaseContract.TicketEntry.TABLE_NAME,
				new String[]{},
				DatabaseContract.TicketEntry.COLUMN_DATE + "= ?",
				new String[]{todayDateStr},
				null, null, null
		);
		boolean hasTodayTicket = cursor.moveToNext();
		cursor.close();
		return hasTodayTicket;
	}
	
	public void storeTicket(Ticket ticket) {
		ContentValues insertValues = new ContentValues();
		insertValues.put(DatabaseContract.TicketEntry.COLUMN_ZOO_ID, ticket.getZooID());
		insertValues.put(DatabaseContract.TicketEntry.COLUMN_DATE, dateFormat.format(ticket.getDate()));
		database.insert(
				DatabaseContract.TicketEntry.TABLE_NAME,
				null,
				insertValues
		);
	}
	
	// Groups----------------------------------------------------------
	public Map<Integer, Group> getAllGroups() {
		Cursor cursor = database.query(
				DatabaseContract.GroupEntry.TABLE_NAME,
				new String[]{
						DatabaseContract.GroupEntry._ID,
						DatabaseContract.GroupEntry.COLUMN_NAME,
						DatabaseContract.GroupEntry.COLUMN_IMAGE
				},
				null, null, null, null,
				DatabaseContract.GroupEntry.COLUMN_NAME + " ASC"
		);
		
		Map<Integer, Group> groupMap = new HashMap<>();
		int id;
		String name;
		byte[] imageBlob;
		
		Group tmpGroup;
		while (cursor.moveToNext()) {
			id = cursor.getInt(cursor.getColumnIndex(DatabaseContract.GroupEntry._ID));
			name = cursor.getString(cursor.getColumnIndex(DatabaseContract.GroupEntry.COLUMN_NAME));
			imageBlob = cursor.getBlob(cursor.getColumnIndex(DatabaseContract.GroupEntry.COLUMN_IMAGE));
			if (imageBlob != null)
				tmpGroup = new Group(id, name, BitmapFactory.decodeByteArray(imageBlob, 0, imageBlob.length), getGroupAttributes(id));
			else
				tmpGroup = new Group(id, name, null, getGroupAttributes(id));
			
			groupMap.put(id, tmpGroup);
		}
		cursor.close();
		return groupMap;
	}
	
	private List<Attribute> getGroupAttributes(int groupID) {
		return getAttributes(groupID, DatabaseContract.GroupAttributesEntry.TABLE_NAME);
	}
	
	public Map<Integer, List<Integer>> getGroupsSpeciesIdsMap() {
		Map<Integer, List<Integer>> groupsSpeciesMap = new HashMap<>();
		
		Cursor cursor = database.query(
				DatabaseContract.GroupsSpeciesEntry.TABLE_NAME,
				new String[]{
						DatabaseContract.GroupsSpeciesEntry._ID,
						DatabaseContract.GroupsSpeciesEntry.COLUMN_GROUP_ID,
						DatabaseContract.GroupsSpeciesEntry.COLUMN_SPECIES_ID
				},
				null, null, null, null, null
		);
		int groupId, speciesId;
		while (cursor.moveToNext()) {
			groupId = cursor.getInt(cursor.getColumnIndex(DatabaseContract.GroupsSpeciesEntry.COLUMN_GROUP_ID));
			speciesId = cursor.getInt(cursor.getColumnIndex(DatabaseContract.GroupsSpeciesEntry.COLUMN_SPECIES_ID));
			if (!groupsSpeciesMap.containsKey(groupId)) {
				groupsSpeciesMap.put(groupId, new ArrayList<>());
				
			}
			groupsSpeciesMap.get(groupId).add(speciesId);
		}
		cursor.close();
		return groupsSpeciesMap;
	}
	
	// Species----------------------------------------------------------
	public Map<Integer, Species> getAllSpecies() {
		Cursor cursor = database.query(
				DatabaseContract.SpeciesEntry.TABLE_NAME,
				new String[]{
						DatabaseContract.SpeciesEntry._ID,
						DatabaseContract.SpeciesEntry.COLUMN_NAME,
						DatabaseContract.SpeciesEntry.COLUMN_IMAGE,
						DatabaseContract.SpeciesEntry.COLUMN_LOCATION_ID,
						DatabaseContract.SpeciesEntry.COLUMN_WEIGHT,
						DatabaseContract.SpeciesEntry.COLUMN_SIZE
				},
				null, null, null, null,
				DatabaseContract.SpeciesEntry.COLUMN_NAME + " ASC"
		);
		Map<Integer, Species> speciesMap = new HashMap<>();
		int id;
		String name, weight, size;
		byte[] imageBlob;
		Pair<Double, Double> location;
		List<Attribute> attributes;
		Species tmpSpecies;
		while (cursor.moveToNext()) {
			id = cursor.getInt(cursor.getColumnIndex(DatabaseContract.SpeciesEntry._ID));
			name = cursor.getString(cursor.getColumnIndex(DatabaseContract.SpeciesEntry.COLUMN_NAME));
			imageBlob = cursor.getBlob(cursor.getColumnIndex(DatabaseContract.SpeciesEntry.COLUMN_IMAGE));
			location = this.getLocation(cursor.getInt(cursor.getColumnIndex(DatabaseContract.SpeciesEntry.COLUMN_LOCATION_ID)));
			weight = cursor.getString(cursor.getColumnIndex(DatabaseContract.SpeciesEntry.COLUMN_WEIGHT));
			size = cursor.getString(cursor.getColumnIndex(DatabaseContract.SpeciesEntry.COLUMN_SIZE));
			attributes = this.getSpeciesAttributes(id);
			if (imageBlob != null)
				tmpSpecies = new Species(id, name, BitmapFactory.decodeByteArray(imageBlob, 0, imageBlob.length), weight, size, attributes, location);
			else
				tmpSpecies = new Species(id, name, null, weight, size, attributes, location);
			try {
				tmpSpecies.setMembers(this.getSpeciesIndividuals(tmpSpecies));
			} catch (Exception ignored) {
			}
			speciesMap.put(id, tmpSpecies);
		}
		cursor.close();
		return speciesMap;
	}
	
	private List<Attribute> getSpeciesAttributes(int speciesID) {
		return getAttributes(speciesID, DatabaseContract.SpeciesAttributesEntry.TABLE_NAME);
	}
	
	public byte[] getSpeciesAudio(int speciesID) {
		Cursor cursor = database.query(
				DatabaseContract.SpeciesEntry.TABLE_NAME,
				new String[]{DatabaseContract.SpeciesEntry.COLUMN_AUDIO},
				DatabaseContract.SpeciesEntry._ID + " = ?",
				new String[]{String.valueOf(speciesID)},
				null, null, null
		);
		cursor.moveToNext();
		byte[] audioBlob = cursor.getBlob(cursor.getColumnIndex(DatabaseContract.SpeciesEntry.COLUMN_AUDIO));
		cursor.close();
		return audioBlob;
	}
	
	// Individuals----------------------------------------------------------
	private List<Subject> getSpeciesIndividuals(Species species) {
		Cursor cursor = database.query(
				DatabaseContract.IndividualEntry.TABLE_NAME,
				new String[]{
						DatabaseContract.IndividualEntry._ID,
						DatabaseContract.IndividualEntry.COLUMN_NAME,
						DatabaseContract.IndividualEntry.COLUMN_DOB,
						DatabaseContract.IndividualEntry.COLUMN_PLACE_OF_BIRTH,
						DatabaseContract.IndividualEntry.COLUMN_GENDER,
				},
				DatabaseContract.IndividualEntry.COLUMN_SPECIES_ID + " = ?",
				new String[]{Integer.toString(species.getDatabaseID())},
				null, null,
				DatabaseContract.IndividualEntry.COLUMN_NAME + " ASC"
		);
		
		List<Subject> individualList = new ArrayList<>();
		int id;
		String name, placeOfBirth, gender;
		Date dob = new Date();
		while (cursor.moveToNext()) {
			id = cursor.getInt(cursor.getColumnIndex(DatabaseContract.IndividualEntry._ID));
			name = cursor.getString(cursor.getColumnIndex(DatabaseContract.IndividualEntry.COLUMN_NAME));
			try {
				dob = dateFormat.parse(cursor.getString(cursor.getColumnIndex(DatabaseContract.IndividualEntry.COLUMN_DOB)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			placeOfBirth = cursor.getString(cursor.getColumnIndex(DatabaseContract.IndividualEntry.COLUMN_PLACE_OF_BIRTH));
			gender = cursor.getString(cursor.getColumnIndex(DatabaseContract.IndividualEntry.COLUMN_GENDER));
			
			individualList.add(new Individual(id, species, name, dob, placeOfBirth, gender, getIndividualAttributes(id)));
		}
		cursor.close();
		return individualList;
	}
	
	private List<Attribute> getIndividualAttributes(int individualID) {
		return getAttributes(individualID, DatabaseContract.IndividualAttributesEntry.TABLE_NAME);
	}
	
	public Bitmap getIndividualImage(int individualID) {
		Cursor cursor = database.query(
				DatabaseContract.IndividualEntry.TABLE_NAME,
				new String[]{
						DatabaseContract.IndividualEntry.COLUMN_IMAGE
				},
				DatabaseContract.IndividualEntry._ID + " = ?",
				new String[]{Integer.toString(individualID)},
				null, null, null
		);
		cursor.moveToNext();
		byte[] imageBlob = cursor.getBlob(cursor.getColumnIndex(DatabaseContract.IndividualEntry.COLUMN_IMAGE));
		cursor.close();
		if (imageBlob != null)
			return BitmapFactory.decodeByteArray(imageBlob, 0, imageBlob.length);
		else
			return null;
	}
	
	// Other----------------------------------------------------------------
	private Pair<Double, Double> getLocation(int locationID) {
		Cursor cursor = database.query(
				DatabaseContract.LocationEntry.TABLE_NAME,
				new String[]{ DatabaseContract.LocationEntry.COLUMN_COORDINATES },
				DatabaseContract.SpeciesEntry._ID + "= ?", new String[]{Integer.toString(locationID)},
				null, null, null
		);
		Pair<Double, Double> location = null;
		if (cursor.moveToNext()) {
			try {
				String[] coordinates = cursor.getString(cursor.getColumnIndex(DatabaseContract.LocationEntry.COLUMN_COORDINATES)).split(",");
				location = new Pair<>(Double.parseDouble(coordinates[0]), Double.parseDouble(coordinates[1]));
			} catch(Exception ignored){}
		}
		cursor.close();
		return location;
	}
	
	private List<Attribute> getAttributes(int subjectID, String tableName) {
		List<Attribute> attributes = new ArrayList<>();
		Cursor cursor = database.rawQuery(
				"select attributeCategories." + DatabaseContract.AttributeCategoryEntry.COLUMN_NAME + " , " +
						" subjectAttributes." + DatabaseContract.AttributesColumns.COLUMN_ATTRIBUTE +
						" from " + tableName + " as subjectAttributes inner join " + DatabaseContract.AttributeCategoryEntry.TABLE_NAME + " as attributeCategories on " +
						" subjectAttributes." + DatabaseContract.AttributesColumns.COLUMN_CATEGORY_ID + " = attributeCategories." + DatabaseContract.AttributeCategoryEntry._ID +
						" where subjectAttributes." + DatabaseContract.AttributesColumns.COLUMN_SUBJECT_ID + "=? " +
						"order by attributeCategories." + DatabaseContract.AttributeCategoryEntry.COLUMN_POSITION + " asc ",
				new String[]{Integer.toString(subjectID)}
		);
		while (cursor.moveToNext()) {
			attributes.add(new Attribute(
							cursor.getString(cursor.getColumnIndex(DatabaseContract.AttributeCategoryEntry.COLUMN_NAME)),
							cursor.getString(cursor.getColumnIndex(DatabaseContract.AttributesColumns.COLUMN_ATTRIBUTE))
					)
			);
		}
		cursor.close();
		return attributes;
	}
}
