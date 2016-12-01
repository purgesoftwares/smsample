package com.allowify.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.allowify.model.VIPCode;

@RepositoryRestResource(collectionResourceRel = "vip_codes", path = "vip-codes")
public interface VIPCodeRepositoryCustom extends MongoRepository<VIPCode, String>, 
																CrudRepository<VIPCode, String>{
	
	public List<VIPCode> findById(@Param("id") String id);
	
	public VIPCode findByCode(@Param("code") String code);
	
	public List<VIPCode> findByCodeLike(String code, Pageable pageable);
	
}
