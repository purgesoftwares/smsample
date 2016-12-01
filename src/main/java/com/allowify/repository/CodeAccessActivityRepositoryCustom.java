package com.allowify.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.allowify.model.Code;
import com.allowify.model.CodeAccessActivity;


@RepositoryRestResource(collectionResourceRel = "code-access-activity", path = "code-access-activities")
public interface CodeAccessActivityRepositoryCustom extends MongoRepository<CodeAccessActivity, String>,
															  CrudRepository<CodeAccessActivity, String>{
	
	public List<CodeAccessActivity> findById(@Param("id") String id);
	
	public CodeAccessActivity findByCode(@Param("code") Code code);

}
