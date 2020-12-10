package com.zooverse.model;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class Species {
	private final int id;
	private final String name;
	private final String description;
	private final Bitmap image;
	private List<Individual> individuals;
	
	public Species(int id, String name, String description, Bitmap image) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.image = image;
		this.individuals = new ArrayList<>();
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
		return this.description;
	}
	
	public byte[] getAudio() {
		return Model.getSpeciesAudio(this.id);
	}
	
	public List<Individual> getIndividuals() {
		return this.individuals;
	}
	
	public void setIndividuals(List<Individual> individuals){
		this.individuals = individuals;
	}
}
