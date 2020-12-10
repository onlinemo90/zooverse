package com.zooverse.model;


import android.graphics.Bitmap;
import android.util.Pair;

import com.zooverse.model.database.DatabaseHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Model {
	private static final DatabaseHandler dbHandler = new DatabaseHandler();
	
	private static final List<Ticket> storedTickets = initStoredTickets();
	private static final Map<Integer, Species> species = initSpecies();
	
	private Model() {
		// prevent class initialisation
	}
	
	public static void init() {
		// Empty method to initialise constants
	}
	
	private static List<Ticket> initStoredTickets() {
		return dbHandler.getStoredTickets(new Date());
	}
	
	private static Map<Integer, Species> initSpecies() {
		return dbHandler.getAllSpecies();
	}
	
	public static List<Ticket> getStoredTickets() {
		return storedTickets;
	}
	
	public static boolean hasTodayTicket() {
		for (Ticket ticket : storedTickets) {
			if (ticket.isForToday()) {
				return true;
			}
		}
		return false;
	}
	
	public static void storeTicket(Ticket ticket) {
		dbHandler.storeTicket(ticket);
		storedTickets.add(ticket);
		Collections.sort(storedTickets, (Ticket t1, Ticket t2) -> t1.getDate().compareTo(t2.getDate()));
	}
	
	public static Map<Integer, Species> getSpecies() {
		return species;
	}
	
	public static List<Species> getSortedSpeciesList() {
		List<Species> speciesList = new ArrayList<>(species.values());
		Collections.sort(speciesList, (Species s1, Species s2) -> s1.getName().compareTo(s2.getName()));
		return speciesList;
	}
	
	public static byte[] getSpeciesAudio(int speciesID) {
		return dbHandler.getSpeciesAudio(speciesID);
	}
	
	public static Bitmap getIndividualImage(int individualID) {
		return dbHandler.getIndividualImage(individualID);
	}
}
