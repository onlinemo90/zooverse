package com.zooverse.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Species {
	private String name;
	private String description;
	Bitmap image;
	private byte[] audio; //TODO: Audio of what? This doesn't read well as a property of a species
	
	public Species(String name, String description, byte[] imgBlob, byte[] audio) {
		this.name = name;
		this.description = description;
		this.image = BitmapFactory.decodeByteArray(imgBlob, 0, imgBlob.length);
		this.audio = audio;
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
	
	public byte[] getAudio() {
		return this.audio;
	}
}
