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
	
	private static final Map<Integer, Species> species = initSpecies();
	private static final Map<Integer, Group> groups = initGroups();
	private static final List<Pair<Integer,Integer>> speciesInGroupsRegister = initSpeciesInGroupsRegister();
	
	private Model() {
		// prevent class initialisation
	}
	
	public static void init() {
		// Empty method to initialise constants
	}
	
	// Tickets----------------------------------------------------------
	public static List<Ticket> getStoredTickets() {
		return dbHandler.getStoredTickets(new Date());
	}
	
	public static boolean hasTodayTicket() {
		return dbHandler.hasTodayTicket();
	}
	
	public static void storeTicket(Ticket ticket) {
		dbHandler.storeTicket(ticket);
	}
	
	// Groups----------------------------------------------------------
	private static Map<Integer, Group> initGroups() {
		return dbHandler.getAllGroups();
	}
	
	private static List<Pair<Integer,Integer>> initSpeciesInGroupsRegister() {
		return dbHandler.getSpeciesInGroups();
	}
	
	public static Map<Integer, Group> getGroups() {
		return groups;
	}
	
	public static List<Species> getSpeciesOfGroup(int groupId) {
		List<Species> speciesOfGroup= new ArrayList<>();
		for (Pair<Integer, Integer> speciesInGroup : speciesInGroupsRegister) {
			if (speciesInGroup.first == groupId)
				speciesOfGroup.add(species.get(speciesInGroup.second));
		}
		return speciesOfGroup;
	}
	
	// Species----------------------------------------------------------
	private static Map<Integer, Species> initSpecies() {
		return dbHandler.getAllSpecies();
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
	
	// Individuals----------------------------------------------------------
	public static Bitmap getIndividualImage(int individualID) {
		return dbHandler.getIndividualImage(individualID);
	}
}
