package com.allowify.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.allowify.model.EventInterested;

@RepositoryRestResource(collectionResourceRel = "interest", path = "interests")
public interface EventInterestedRepositoryCustom extends MongoRepository<EventInterested, String>,
														CrudRepository<EventInterested, String> {
	
	public EventInterested findByEventIdAndUserId(@Param("eventId") String eventId, 
			@Param("userId") String userId);
	
	public List<EventInterested> findByEventId(@Param("eventId") String eventId);

}
