package com.allowify.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.allowify.model.Code;
import com.allowify.model.Review;

@RepositoryRestResource(collectionResourceRel="reviews", path="reviews")
public interface ReviewRepositoryCustom extends MongoRepository<Review, String>,
													CrudRepository<Review, String> {
	
	public Review findById(@Param("id") String id);
	
	public List<Review> findByUserId(@Param("userId") String userId);
	
	public List<Review> findByCode(@Param("code") String code);


}
