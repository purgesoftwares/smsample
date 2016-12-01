package com.allowify.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.allowify.model.UserPoint;

@RepositoryRestResource(collectionResourceRel = "point", path = "points")
public interface PointRepositoryCustom  extends MongoRepository<UserPoint, String>,
													CrudRepository<UserPoint, String> {
	
	public List<UserPoint> findByUserId(@Param("userId") String userId);
	

}
