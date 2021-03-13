package com.zooverse.model;

import android.graphics.Bitmap;
import android.util.Pair;

import java.util.List;

public abstract class Subject {
	protected final int id;
	protected final String name;
	protected final Bitmap image;
	protected final List<Pair<String, String>> attributes;
	
	public Subject(int id, String name, Bitmap image, List<Pair<String, String>> attributes) {
		this.id = id;
		this.name = name;
		this.image = image;
		this.attributes = attributes;
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
	
	public List<Pair<String, String>> getAttributes() {
		return this.attributes;
	}
}
