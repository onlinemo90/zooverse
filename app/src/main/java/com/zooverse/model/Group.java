package com.zooverse.model;

import android.graphics.Bitmap;

import java.util.Collections;
import java.util.List;

public class Group extends AbstractSubject{

	private List<AbstractSubject> members;
	
	public Group(int id, String name, Bitmap image) {
		super (id, name, image);
	}
	
	public List<AbstractSubject> getMembers() {
		return this.members;
	}
	
	public void setMembers(List<AbstractSubject> members){
		this.members = members;
		this.members.sort((AbstractSubject s1, AbstractSubject s2) -> s1.getName().compareTo(s2.getName()));
	}
}
