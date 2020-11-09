package com.zooverse.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Species {
	private final int id;
	private final String name;
	private final Bitmap image;
	
	public Species(int id, String name, byte[] imgBlob) {
		this.id = id;
		this.name = name;
		this.image = BitmapFactory.decodeByteArray(imgBlob, 0, imgBlob.length);
	}
	
	public String getName() {
		return this.name;
	}
	
	public Bitmap getImage() {
		return this.image;
	}
	
	public String getDescription() {
		return Model.getSpeciesDescription(this.id);
	}
	
	public byte[] getAudioDescription() {
		return Model.getSpeciesAudioDescription(this.id);
	}
	
}
