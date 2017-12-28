package com.gary.blog.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
public class Note {
	@Id
	private String id;
	private String category;
	private String name;
	private String content;
	
	public Note(String name,String content) {
		super();
		this.name=name;
		this.content=content;
	}
	public Note() {
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getCategory() {
		return this.category;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getContent() {
		return this.content;
	}
	
	public void setCategory(String category) {
		this.category=category;
	}
	
	public void setName(String name) {
		this.name=name;
	}
	
	public void setContent(String content) {
		this.content=content;
	}
	
	public void setId(String id) {
		this.id=id;
	}
}
