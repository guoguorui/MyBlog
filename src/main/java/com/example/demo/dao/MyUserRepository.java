package com.example.demo.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.domain.MyUser;

public interface MyUserRepository extends MongoRepository<MyUser,String>{
	MyUser findByUsername(String username);
}
