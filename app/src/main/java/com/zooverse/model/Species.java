package com.zooverse.model;

import android.graphics.Bitmap;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Species {
	private final int id;
	private final String name;
	private final String description;
	private final Bitmap image;
	private List<Individual> individuals;
	private final Pair<Double, Double> location;
	
	public Species(int id, String name, String description, Bitmap image, Pair<Double, Double> location) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.image = image;
		this.individuals = new ArrayList<>();
		this.location = location;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getNamePlural() {
		return this.name + "s";
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
	
	public Pair<Double, Double> getLocation() {
		return this.location;
	}
	
	public List<Individual> getIndividuals() {
		return this.individuals;
	}
	
	public void setIndividuals(List<Individual> individuals) {
		this.individuals = individuals;
	}
}
