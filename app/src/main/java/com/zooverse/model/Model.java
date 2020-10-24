package com.zooverse.model;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.zooverse.model.database.DatabaseHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Model {
	private static final DatabaseHandler dbHandler = new DatabaseHandler();
	
	private static final List<Ticket> storedTickets = initStoredTickets();
	private static final List<Species> speciesList = initSpeciesList();
	
	private Model() {
		// prevent class initialisation
	}
	
	private static List<Ticket> initStoredTickets() {
		return dbHandler.getStoredTickets(Ticket.getTodayFormattedDate());
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
	
	public static Bitmap getSpeciesImage(int speciesID){
		byte[] imgBlob = dbHandler.getSpeciesImage(speciesID);
		return BitmapFactory.decodeByteArray(imgBlob, 0, imgBlob.length);
	}
	
	public static byte[] getSpeciesAudioDescription(int speciesID){
		return dbHandler.getSpeciesAudioDescription(speciesID);
	}
}
