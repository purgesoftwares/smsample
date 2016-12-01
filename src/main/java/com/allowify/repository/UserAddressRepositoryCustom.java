package com.allowify.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.allowify.model.UserAddress;

@RepositoryRestResource(collectionResourceRel = "user-address", path = "user-address")
public interface UserAddressRepositoryCustom extends MongoRepository<UserAddress, String>,
													 CrudRepository<UserAddress, String>{

}
