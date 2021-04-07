package com.zooverse.zoo;

import android.graphics.Bitmap;
import android.util.Pair;

import java.io.InvalidObjectException;
import java.util.List;
import java.util.UUID;

public abstract class Subject {
	private final String uuid;
	protected final int databaseID;
	protected final String name;
	protected final Bitmap image;
	protected final Pair<Double, Double> location;
	protected final List<Attribute> attributes;
	protected List<Subject> members;
	
	public Subject(int databaseID, String name, Bitmap image, Pair<Double, Double> location, List<Attribute> attributes) {
		this.uuid = UUID.randomUUID().toString();
		this.databaseID = databaseID;
		this.name = name;
		this.image = image;
		this.location = location; //TODO: change to Android Location API
		this.attributes = attributes;
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public int getDatabaseID() {
		return this.databaseID;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Bitmap getImage() {
		return this.image;
	}
	
	public byte[] getAudio() {
		return null;
	}
	
	public Pair<Double, Double> getLocation() {
		return this.location;
	}
	
	public List<Attribute> getAttributes() {
		return this.attributes;
	}
	
	public List<Subject> getMembers() {
		return this.members;
	}
	
	public void setMembers(List<Subject> members) throws InvalidObjectException {
		this.members = members;
		this.members.sort((Subject s1, Subject s2) -> s1.getName().compareTo(s2.getName()));
	}
	
}
