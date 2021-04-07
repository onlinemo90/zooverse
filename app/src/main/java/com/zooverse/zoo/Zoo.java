package com.zooverse.zoo;


import android.graphics.Bitmap;

import com.zooverse.zoo.database.DatabaseHandler;

import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Zoo {
	private static final DatabaseHandler dbHandler = DatabaseHandler.getInstance();
	
	private static final Map<String, Subject> subjectsMap = initSubjects();
	
	private Zoo() {
		// prevent class initialisation
	}
	
	public static void init() {
		// Empty method to initialise constants
	}
	
	private static Map<String, Subject> initSubjects() {
		Map<Integer, Species> speciesMap = initSpecies();
		Map<Integer, Individual> individualsMap = initIndividuals(speciesMap);
		Map<Integer, Group> groupsMap = initGroups(speciesMap, individualsMap);
		
		Map<String, Subject> allSubjects = new HashMap<>();
		for (Individual individual : individualsMap.values()) {
			allSubjects.put(individual.getUuid(), individual);
		}
		for (Species tmpSpecies : speciesMap.values()) {
			allSubjects.put(tmpSpecies.getUuid(), tmpSpecies);
		}
		for (Group group : groupsMap.values()) {
			allSubjects.put(group.getUuid(), group);
		}
		return allSubjects;
	}
	
	private static Map<Integer, Species> initSpecies() {
		return dbHandler.getAllSpecies();
	}
	
	private static Map<Integer, Individual> initIndividuals(Map<Integer, Species> allSpeciesMap) {
		Map<Integer, Individual> individualsMap = new HashMap<>();
		for (Species species : allSpeciesMap.values()) {
			for (Subject individual : species.getMembers()) {
				individualsMap.put(individual.getDatabaseID(), (Individual) individual);
			}
		}
		return individualsMap;
	}
	
	private static Map<Integer, Group> initGroups(Map<Integer, Species> allSpeciesMap, Map<Integer, Individual> allIndividualsMap) {
		Map<Integer, Group> groups = dbHandler.getAllGroups();
		Map<Integer, List<Integer>> groupsSpeciesIdsMap = dbHandler.getGroupsSpeciesIdsMap();
		List<Subject> groupSpeciesList;
		for (Map.Entry<Integer, Group> groupEntry : groups.entrySet()) {
			if (groupsSpeciesIdsMap.containsKey(groupEntry.getKey())) {
				groupSpeciesList = new ArrayList<>();
				for (Integer speciesId : groupsSpeciesIdsMap.get(groupEntry.getKey())) {
					groupSpeciesList.add(allSpeciesMap.get(speciesId));
				}
				try {
					groupEntry.getValue().setMembers(groupSpeciesList);
				} catch (InvalidObjectException e) {
					e.printStackTrace();
				}
			}
		}
		// TODO: Add support for individuals in groups
		return groups;
	}
	
	// Tickets--------------------------------------------------------------------------------------
	public static List<Ticket> getStoredTickets() {
		return dbHandler.getStoredTickets(new Date());
	}
	
	public static boolean hasTodayTicket() {
		return dbHandler.hasTodayTicket();
	}
	
	public static void storeTicket(Ticket ticket) {
		dbHandler.storeTicket(ticket);
	}
	
	
	// Subjects-------------------------------------------------------------------------------------
	public static List<Subject> getSubjectList() {
		List<Subject> subjectsList = new ArrayList<>(subjectsMap.values());
		subjectsList.sort((Subject s1, Subject s2) -> (s1.getName().compareTo(s2.getName())));
		return subjectsList;
	}
	
	public static List<Subject> getSubjectList(Collection<String> uuidList) {
		List<Subject> filteredSubjectsList = new ArrayList<>();
		for (Subject subject : getSubjectList()) {
			if (uuidList.contains(subject.getUuid())) {
				filteredSubjectsList.add(subject);
			}
		}
		return filteredSubjectsList;
	}
	
	public static List<Subject> getSubjectList(Class... subjectTypes) {
		List<Subject> filteredSubjectsList = new ArrayList<>();
		for (Subject subject : getSubjectList()) {
			for (Class subjectType : subjectTypes) {
				if (subjectType.isInstance(subject)) {
					filteredSubjectsList.add(subject);
				}
			}
		}
		return filteredSubjectsList;
	}
	
	public static List<Subject> getSearchableSubjectsList() {
		return getSubjectList(Species.class, Group.class);
	}
}
