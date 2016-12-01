package com.allowify.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.allowify.model.Alert;

@RepositoryRestResource(collectionResourceRel = "alerts", path = "alerts")
public interface AlertRepositoryCustom  extends MongoRepository<Alert, String>,CrudRepository<Alert,String> {

	 public List<Alert> findByCardNumber(@Param("cardNumber") Long cardNumber);
	 
	 public List<Alert> findByUserId(@Param("userId") String userId, Sort sort);
	 	 
}

