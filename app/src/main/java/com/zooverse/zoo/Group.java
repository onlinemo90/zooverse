package com.zooverse.zoo;

import android.graphics.Bitmap;

import java.util.List;

public class Group extends Subject {
	public Group(int id, String name, Bitmap image, List<Attribute> attributes) {
		super (id, name, image, null, attributes);
	}
}
