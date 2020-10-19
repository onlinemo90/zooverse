package com.zooverse.model;


import com.zooverse.model.database.DatabaseHandler;

import java.util.Collections;
import java.util.List;

public class Model {
	private static final DatabaseHandler dbHelper = new DatabaseHandler();
	
	private static final List<Ticket> storedTickets = initStoredTickets();
	private static final List<Species> speciesList = initSpeciesList();
	
	private Model() {
		// prevent class initialisation
	}
	
	private static List<Ticket> initStoredTickets() {
		return dbHelper.getStoredTickets(Ticket.getTodayFormattedDate());
	}
	
	private static List<Species> initSpeciesList() {
		return dbHelper.getAllSpecies();
	}
	
	public static List<Ticket> getStoredTickets() {
		return storedTickets;
	}
	
	public static List<Species> getSpeciesList() {
		return speciesList;
	}
	
	public static void storeTicket(Ticket ticket) {
		dbHelper.storeTicket(ticket);
		storedTickets.add(ticket);
		Collections.sort(storedTickets, (Ticket t1, Ticket t2) -> t1.getDate().compareTo(t2.getDate()));
	}
	
}
