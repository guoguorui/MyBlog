package com.example.demo.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.domain.Note;

public interface NoteRepository extends MongoRepository<Note, String>{
	
	Note findByName(String name);

}
