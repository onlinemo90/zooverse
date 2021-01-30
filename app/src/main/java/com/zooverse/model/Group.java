package com.zooverse.model;

import android.graphics.Bitmap;

import java.util.Collections;
import java.util.List;

public class Group {
	private final int id;
	private final String name;
	private final Bitmap image;
	private List<Species> speciesList;
	
	public Group(int id, String name, Bitmap image) {
		this.id = id;
		this.name = name;
		this.image = image;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Bitmap getImage() {
		return this.image;
	}
	
	public List<Species> getSpecies() {
		if (speciesList == null) {
			speciesList = Model.getSpeciesOfGroup(this.id);
			Collections.sort(speciesList, (Species s1, Species s2) -> s1.getName().compareTo(s2.getName()));
		}
		return this.speciesList;
	}
}
