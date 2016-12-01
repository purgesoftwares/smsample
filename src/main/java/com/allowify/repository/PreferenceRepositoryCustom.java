package com.allowify.repository;

import java.util.List;



import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.repository.CrudRepository;

import com.allowify.model.Preference;

@RepositoryRestResource(collectionResourceRel = "preferences", path = "preferences")
public interface PreferenceRepositoryCustom  extends MongoRepository<Preference, String>,CrudRepository<Preference,String> {

	 public List<Preference> findByIsCardActive(Boolean isCardActive);
	 
}

