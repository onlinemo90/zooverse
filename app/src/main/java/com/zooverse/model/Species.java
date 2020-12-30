package com.zooverse.model;

import android.graphics.Bitmap;
import android.util.Pair;

import com.zooverse.MainApplication;
import com.zooverse.R;

import java.util.ArrayList;
import java.util.List;

public class Species {
	private final int id;
	private final String name;
	private final Bitmap image;
	private List<Individual> individuals;
	private final List<Pair<String, String>> attributes;
	private final Pair<Double, Double> location;
	
	public Species(int id, String name, Bitmap image, List<Pair<String, String>> attributes, Pair<Double, Double> location) {
		this.id = id;
		this.name = name;
		this.image = image;
		this.individuals = new ArrayList<>();
		this.attributes = attributes;
		this.location = location;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getNamePlural() {
		return this.getName() + MainApplication.getContext().getString(R.string.species_title_suffix);
	}
	
	public Bitmap getImage() {
		return this.image;
	}
	
	public byte[] getAudio() {
		return Model.getSpeciesAudio(this.id);
	}
	
	public List<Pair<String, String>> getAttributes() {
		return this.attributes;
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
