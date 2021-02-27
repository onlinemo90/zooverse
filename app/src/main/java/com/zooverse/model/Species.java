package com.zooverse.model;

import android.graphics.Bitmap;
import android.util.Pair;

import com.zooverse.MainApplication;
import com.zooverse.R;

import java.util.ArrayList;
import java.util.List;

public class Species extends Subject {
	private List<Individual> individuals;
	private final String weight;
	private final String size;
	private final List<Pair<String, String>> attributes;
	private final Pair<Double, Double> location;
	
	public Species(int id, String name, Bitmap image, String weight, String size, List<Pair<String, String>> attributes, Pair<Double, Double> location) {
		super (id, name, image);
		this.individuals = new ArrayList<>();
		this.weight = weight;
		this.size = size;
		this.attributes = attributes;
		this.location = location;
	}
	
	public String getNamePlural() {
		return this.getName() + MainApplication.getContext().getString(R.string.species_title_suffix);
	}
	
	public byte[] getAudio() {
		return Model.getSpeciesAudio(this.id);
	}
	
	public String getWeight() {
		return weight;
	}
	
	public String getSize() {
		return size;
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
