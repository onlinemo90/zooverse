package com.zooverse.model;

import android.graphics.Bitmap;
import android.util.Pair;

import static com.zooverse.MainApplication.getContext;
import com.zooverse.R;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class Individual {
	
	private final int id;
	private final Species species;
	private final String name;
	private final Date dob;
	private final String placeOfBirth;
	private final String gender;
	private final String weight;
	private final String size;
	private final List<Pair<String, String>> attributes;
	
	public Individual(int id, Species species, String name, Date dob, String placeOfBirth, String gender, String weight, String size, List<Pair<String, String>> attributes) {
		this.id = id;
		this.species = species;
		this.name = (name != null ? name : "");
		this.dob = dob;
		this.placeOfBirth = placeOfBirth;
		this.gender = gender;
		this.weight = weight;
		this.size = size;
		this.attributes = attributes;
	}
	
	public int getId() {
		return id;
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
	
	public String getAge() {
		
		if ((this.getDOB() != null)) {
			Period age = Period.between(getDOB().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), LocalDate.now());
			
			if (age.getYears() > 0) {
				return age.getYears() + getContext().getString(R.string.year_abbreviation);
			} else if (age.getMonths() > 0) {
				return age.getMonths() + getContext().getString(R.string.month_abbreviation);
			} else if (age.getDays() > 0) {
				return age.getDays() + getContext().getString(R.string.day_abbreviation);
			}
		}
		return null;
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
	
	public List<Pair<String, String>> getAttributes() {
		return this.attributes;
	}
	
	public Bitmap getImage() {
		return Model.getIndividualImage(id);
	}
}
