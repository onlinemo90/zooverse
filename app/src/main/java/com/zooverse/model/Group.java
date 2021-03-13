package com.zooverse.model;

import android.graphics.Bitmap;
import android.util.Pair;

import java.util.List;

public class Group extends Subject {

	private List<Subject> members;
	
	public Group(int id, String name, Bitmap image, List<Pair<String, String>> attributes) {
		super (id, name, image, attributes);
	}
	
	public List<Subject> getMembers() {
		return this.members;
	}
	
	public void setMembers(List<Subject> members){
		this.members = members;
		this.members.sort((Subject s1, Subject s2) -> s1.getName().compareTo(s2.getName()));
	}
}
