package com.zooverse.zoo;

import android.graphics.Bitmap;
import android.util.Pair;

import com.zooverse.MainApplication;
import com.zooverse.R;
import com.zooverse.zoo.database.DatabaseHandler;

import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.List;

public class Species extends Subject {
	private final String weight;
	private final String size;
	
	public Species(int id, String name, Bitmap image, String weight, String size, List<Attribute> attributes, Pair<Double, Double> location) {
		super(id, name, image, location, attributes);
		this.weight = weight;
		this.size = size;
	}
	
	public byte[] getAudio() {
		return DatabaseHandler.getInstance().getSpeciesAudio(this.databaseID);
	}
	
	public String getWeight() {
		return weight;
	}
	
	public String getSize() {
		return size;
	}
	
	public void setMembers(List<Subject> members) throws InvalidObjectException {
		for (Subject subject : members) {
			if (!(subject instanceof Individual)) {
				throw new InvalidObjectException("Species can only have Individuals as members");
			}
		}
		this.members = members;
	}
}
