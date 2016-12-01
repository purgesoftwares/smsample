package com.allowify.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.allowify.model.UserActivity;

@RepositoryRestResource(collectionResourceRel = "UserActivity", path = "user-activities")
public interface UserActivityRepositoryCustom extends  MongoRepository<UserActivity, String>, 
								CrudRepository<UserActivity,String>,
								PagingAndSortingRepository<UserActivity, String>{

	public List<UserActivity> findById(@Param("id") String id);
	
	public Page<UserActivity> findByUserId(@Param("userId") String userId, Pageable pageable);
		
	@Query("{ userId: {'$in' : ?0 }}")
	public List<UserActivity> findByUserIds(@Param("userIds") List<String> userIds);
	
	public Page<UserActivity> findByType(@Param("type") String type, Pageable pageable);
	
	
}
