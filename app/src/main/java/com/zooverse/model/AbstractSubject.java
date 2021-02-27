package com.zooverse.model;

import android.graphics.Bitmap;

public abstract class AbstractSubject  {
	protected final int id;
	protected final String name;
	protected final Bitmap image;
	
	public AbstractSubject(int id, String name, Bitmap image) {
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
