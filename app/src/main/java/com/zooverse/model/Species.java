package com.zooverse.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Species {
	private String name;
	private String description;
	Bitmap image;
	private byte[] audioDescription;
	
	public Species(String name, String description, byte[] imgBlob, byte[] audioDescription) {
		this.name = name;
		this.description = description;
		this.image = BitmapFactory.decodeByteArray(imgBlob, 0, imgBlob.length);
		this.audioDescription = audioDescription;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public Bitmap getImage() {
		return this.image;
	}
	
	public byte[] getAudioDescription() {
		return this.audioDescription;
	}
}
