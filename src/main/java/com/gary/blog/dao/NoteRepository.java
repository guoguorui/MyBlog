package com.gary.blog.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.gary.blog.domain.Note;

public interface NoteRepository extends MongoRepository<Note, String>{
	
	Note findByName(String name);

}
