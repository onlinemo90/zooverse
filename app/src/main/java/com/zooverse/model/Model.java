package com.zooverse.model;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.zooverse.model.database.DatabaseHandler;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Model {
	private static final DatabaseHandler dbHandler = new DatabaseHandler();
	
	private static final List<Ticket> storedTickets = initStoredTickets();
	private static final List<Species> speciesList = initSpeciesList();
	
	private Model() {
		// prevent class initialisation
	}
	
	public static void init() {
		// Empty method to initialise constants
	}
	
	private static List<Ticket> initStoredTickets() {
		return dbHandler.getStoredTickets(new Date());
	}
	
	private static List<Species> initSpeciesList() {
		return dbHandler.getAllSpecies();
	}
	
	public static List<Ticket> getStoredTickets() {
		return storedTickets;
	}
	
	public static List<Species> getSpeciesList() {
		return speciesList;
	}
	
	public static void storeTicket(Ticket ticket) {
		dbHandler.storeTicket(ticket);
		storedTickets.add(ticket);
		Collections.sort(storedTickets, (Ticket t1, Ticket t2) -> t1.getDate().compareTo(t2.getDate()));
	}
	
	public static String getSpeciesDescription(int speciesID) {
		return dbHandler.getSpeciesDescription(speciesID);
	}
	
	public static byte[] getSpeciesAudioDescription(int speciesID) {
		return dbHandler.getSpeciesAudioDescription(speciesID);
	}
}
