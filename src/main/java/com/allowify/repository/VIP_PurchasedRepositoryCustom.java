package com.allowify.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import com.allowify.model.VIP_Purchased;

@RepositoryRestResource(collectionResourceRel = "vip-purchase", path = "vip-purchased")
public interface VIP_PurchasedRepositoryCustom extends MongoRepository<VIP_Purchased, String> ,
														CrudRepository<VIP_Purchased, String>{

	public List<VIP_Purchased> findById(@Param("id") String id);

}
