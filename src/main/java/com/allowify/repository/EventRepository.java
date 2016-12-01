package com.allowify.repository;


import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.allowify.model.Event;


/**
 * 
 * @author Bharat
 *
 */
@RepositoryRestResource(collectionResourceRel = "event", path = "event")
public interface EventRepository extends PagingAndSortingRepository<Event, Long>{

}

