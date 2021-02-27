package com.zooverse.model;

import android.graphics.Bitmap;

import java.util.List;

public class Group extends Subject {

	private List<Subject> members;
	
	public Group(int id, String name, Bitmap image) {
		super (id, name, image);
	}
	
	public List<Subject> getMembers() {
		return this.members;
	}
	
	public void setMembers(List<Subject> members){
		this.members = members;
		this.members.sort((Subject s1, Subject s2) -> s1.getName().compareTo(s2.getName()));
	}
}
