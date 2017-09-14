package com.example.demo.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Comment {
	
	@Id
	private String id;
	private String content;
	private String username;
	private String total;
	
	public String getTotal() {
		return total;
	}
	
	public void setTotal(String total) {
		this.total=total;
	}
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getContent() {
		return this.content;
	}
	
	public void setContent(String content) {
		this.content=content;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public void setUsername(String username) {
		this.username=username;
	}
	
}
