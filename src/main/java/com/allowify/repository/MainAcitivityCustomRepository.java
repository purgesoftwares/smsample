package com.allowify.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.allowify.model.MainAcitivity;


@RepositoryRestResource(collectionResourceRel = "activities", path = "main-activities")
public interface MainAcitivityCustomRepository extends MongoRepository<MainAcitivity, String>, 
								CrudRepository<MainAcitivity,String>,
								PagingAndSortingRepository<MainAcitivity, String>{

}
