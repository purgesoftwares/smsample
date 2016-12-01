package com.allowify.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import com.allowify.model.CodeChanges;


@RepositoryRestResource(collectionResourceRel = "code-change", path = "code-changes")
public interface CodeChangesRepositoryCustom extends MongoRepository<CodeChanges, String>,
														CrudRepository<CodeChanges, String>{
	
	public List<CodeChanges> findById(@Param("id") String id);

}
