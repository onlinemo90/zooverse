package com.zooverse.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Species {
	private final int id;
	private final String name;
	private final String description;
	
	public Species(int id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public Bitmap getImage() {
		return Model.getSpeciesImage(this.id);
	}
	
	public byte[] getAudioDescription() {
		return Model.getSpeciesAudioDescription(this.id);
	}
	
}
