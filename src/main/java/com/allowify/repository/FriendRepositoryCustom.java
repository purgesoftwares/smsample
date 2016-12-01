package com.allowify.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.allowify.model.Friend;


@RepositoryRestResource(collectionResourceRel = "friend", path = "friends")
public interface FriendRepositoryCustom extends MongoRepository<Friend, String>,
												 CrudRepository<Friend, String> {
	
	public Friend findById(@Param("id") String id);
	
	public List<Friend> findByUserId(@Param("userId") String userId);
	
	public List<Friend> findByAnotherUserId(@Param("anotherUserId") String anotherUserId);

	public List<Friend> findByUserIdOrAnotherUserId(@Param("userId") String userId, 
			@Param("anotherUserId") String anotherUserId);
	
}
