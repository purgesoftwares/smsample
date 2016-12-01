package com.allowify.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.allowify.model.Share;

@RepositoryRestResource(collectionResourceRel="shares", path="share")
public interface ShareRepositoryCustom extends MongoRepository<Share, String>,
											   CrudRepository<Share, String>{
	
	public List<Share> findByReferenceId(@Param("referenceId") String referenceId);

}
