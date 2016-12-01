package com.allowify.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.allowify.model.PromoCode;

@RepositoryRestResource(collectionResourceRel = "promo_code", path = "promo-codes")
public interface PromoCodeRepositoryCustom extends MongoRepository<PromoCode, String>, 
														CrudRepository<PromoCode, String>{
	
	public List<PromoCode> findById(@Param("id") String id);
	
	public PromoCode findByPromoCode(@Param("promoCode") String promoCode);
	
}
