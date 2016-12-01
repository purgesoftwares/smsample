
package com.allowify.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.allowify.model.Person;

@RepositoryRestResource(collectionResourceRel = "people", path = "people")
public interface PersonRepositoryCustom extends MongoRepository<Person, String> {

	public Person findByFirstName(String firstName);
	
	List<Person> findByLastName(@Param("name") String name);

}
