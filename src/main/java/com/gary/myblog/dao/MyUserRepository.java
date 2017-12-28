package com.gary.myblog.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.gary.blog.domain.MyUser;

public interface MyUserRepository extends MongoRepository<MyUser,String>{
	MyUser findByUsername(String username);
}
