package com.zooverse.model;

import android.graphics.Bitmap;

import java.util.List;

public class Subject {
	protected final int id;
	protected final String name;
	protected final Bitmap image;
	
	public Subject(int id, String name, Bitmap image) {
		this.id = id;
		this.name = name;
		this.image = image;
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
}
