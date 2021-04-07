package com.zooverse.zoo;

import android.graphics.Bitmap;

import static com.zooverse.MainApplication.getContext;

import com.zooverse.R;
import com.zooverse.zoo.database.DatabaseHandler;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class Individual extends Subject {
	public enum Gender {
		MALE,
		FEMALE,
		OTHER
	}
	
	private final Species species;
	private final Date dob;
	private final String placeOfBirth;
	private final Gender gender;
	
	public Individual(int id, Species species, String name, Date dob, String placeOfBirth, String gender, List<Attribute> attributes) {
		super(id, name, null, null, attributes);
		this.species = species;
		this.dob = dob;
		this.placeOfBirth = placeOfBirth;
		this.members = null;
		
		if ("M".equals(gender)){
			this.gender = Gender.MALE;
		} else if ("F".equals(gender)){
			this.gender = Gender.FEMALE;
		} else {
			this.gender = Gender.OTHER;
		}
	}
	
	public Species getSpecies() {
		return species;
	}
	
	@Override
	public Bitmap getImage() {
		return DatabaseHandler.getInstance().getIndividualImage(this.databaseID);
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
	
	public Gender getGender() {
		return gender;
	}
	
	@Override
	public void setMembers(List<Subject> subjectList) {
		throw new UnsupportedOperationException();
	}
}
