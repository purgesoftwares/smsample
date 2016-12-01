package com.allowify.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.allowify.model.CodeEvent;

@RepositoryRestResource(collectionResourceRel="code-events", path="events")
public interface CodeEventRepositoryCustom extends MongoRepository<CodeEvent, String> ,
												CrudRepository<CodeEvent, String>,
												PagingAndSortingRepository<CodeEvent, String>{
	
	public CodeEvent findById(@Param("id") String id);
	
	public Page<CodeEvent> findByCode(@Param("code") String code, Pageable page);
	
	@Query("{ code: {'$in' : ?0 }, eventStartTime: {'$gte' :?1 }  }")
	public List<CodeEvent> findByCodes(@Param("codes") List<String> codes,
			@Param("eventStartTime") Date eventStartTime, Sort sort);
	
	public Page<CodeEvent> findByUserId(@Param("userId") String userId, Pageable pageable);
	
	public Page<CodeEvent> findByCodeAndUserId(@Param("code") String code, 
			@Param("userId") String userId, Pageable pageable);
	
	//public CodeEvent findByCode(@Param("code") String code);

}
