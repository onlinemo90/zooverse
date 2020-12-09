package com.zooverse.model;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Species {
	private final int id;
	private final String name;
	private final Bitmap image;
	private List<Individual> individualsList;
	
	public Species(int id, String name, Bitmap image) {
		this.id = id;
		this.name = name;
		this.image = image;
		this.individualsList = new ArrayList<>();
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
	
	public String getDescription() {
		return Model.getSpeciesDescription(this.id);
	}
	
	public byte[] getAudioDescription() {
		return Model.getSpeciesAudioDescription(this.id);
	}
	
	public List<Individual> getIndividualsList() {
		return Model.getSpeciesIndividuals(this);
	}
}
