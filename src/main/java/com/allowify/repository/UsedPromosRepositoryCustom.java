package com.allowify.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.allowify.model.UsedPromos;

@RepositoryRestResource(collectionResourceRel = "used-promos", path = "used-promos")
public interface UsedPromosRepositoryCustom extends MongoRepository<UsedPromos, String>,
													CrudRepository<UsedPromos, String>{
	
	public List<UsedPromos> findByUserIdAndPromoCode(@Param("userId") String userId,
												   @Param("promoCode") String promoCode); 

}
