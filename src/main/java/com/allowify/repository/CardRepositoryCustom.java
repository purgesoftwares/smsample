package com.allowify.repository;

import java.util.List;



import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.repository.CrudRepository;

import com.allowify.model.Card;

@RepositoryRestResource(collectionResourceRel = "cards", path = "cards")
public interface CardRepositoryCustom  extends MongoRepository<Card, String>,CrudRepository<Card,String> {

	 public Card findByCardNumber(Long cardNumber);
	 public List<Card> findByBankName(String bankName);
}
