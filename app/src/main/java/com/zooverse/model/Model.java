package com.zooverse.model;


import android.graphics.Bitmap;

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
	
	// Subjects----------------------------------------------------------
	public static List<AbstractSubject> getSortedSubjectList() {
		List<AbstractSubject> subjectList = new ArrayList<>(species.values());
		subjectList.addAll(new ArrayList<>(groups.values()));
		subjectList.sort((AbstractSubject s1, AbstractSubject s2) -> s1.getName().compareTo(s2.getName()));
		return subjectList;
	}
	
	// Groups----------------------------------------------------------
	private static Map<Integer, Group> initGroups() {
		Map<Integer, Group> groups = dbHandler.getAllGroups();
		Map<Integer, List<Integer>> groupsSpeciesIdsMap = dbHandler.getGroupsSpeciesIdsMap();
		List<AbstractSubject> groupSpeciesList;
		for (Group group : groups.values()){
			if (groupsSpeciesIdsMap.containsKey(group.getId())){
				groupSpeciesList = new ArrayList<>();
				for (Integer speciesId : groupsSpeciesIdsMap.get(group.getId())){
					groupSpeciesList.add(species.get(speciesId));
				}
				group.setMembers(groupSpeciesList);
			}
		}
		return groups;
	}
	
	public static Map<Integer, Group> getGroups() {
		return groups;
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
		speciesList.sort((Species s1, Species s2) -> s1.getName().compareTo(s2.getName()));
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
