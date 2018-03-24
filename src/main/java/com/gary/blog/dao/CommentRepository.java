package com.gary.blog.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.gary.blog.domain.Comment;

public interface CommentRepository extends MongoRepository<Comment, String>{
}
