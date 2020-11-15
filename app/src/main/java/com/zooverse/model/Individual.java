package com.zooverse.model;

import android.graphics.Bitmap;

import java.util.Date;

public class Individual {
	
	private final Species species;
	private final String name;
	private final Date dob;
	private final String placeOfBirth;
	private final String gender;
	private final String weight;
	private final String size;
	private final Bitmap image;
	
	public Individual(Species species, String name, Date dob, String placeOfBirth, String gender, String weight, String size, Bitmap image) {
		this.species = species;
		this.name = name;
		this.dob = dob;
		this.placeOfBirth = placeOfBirth;
		this.gender = gender;
		this.weight = weight;
		this.size = size;
		this.image = image;
	}
	
	public Species getSpecies() {
		return species;
	}
	
	public String getName() {
		return name;
	}
	
	public Date getDOB() {
		return dob;
	}
	
	public String getPlaceOfBirth() {
		return placeOfBirth;
	}
	
	public String getGender() {
		return gender;
	}
	
	public String getWeight() {
		return weight;
	}
	
	public String getSize() {
		return size;
	}
	
	public Bitmap getImage() {
		return image;
	}
}
