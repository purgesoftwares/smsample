package com.allowify.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.allowify.model.CheckIn;

@RepositoryRestResource(collectionResourceRel="check-ins", path="check-ins")
public interface CheckInRepositoryCustom extends MongoRepository<CheckIn, String>,
												CrudRepository<CheckIn, String>{

	public CheckIn findById(@Param("id") String id);
	
	public List<CheckIn> findByCode(@Param("code") String code);
	
	public List<CheckIn> findByUserId(@Param("userId") String userId);
	
	public List<CheckIn> findByUserIdAndCode(@Param("userId") String userId,@Param("code") String code);
}
