package com.zooverse.zoo;


public class Attribute {
	private final String category, text;
	
	public Attribute(String category, String text) {
		this.category = category;
		this.text = text;
	}
	
	public String getCategory() {
		return this.category;
	}
	
	public String getText() {
		return this.text;
	}
}
