package com.allowify.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.allowify.model.Like;

@RepositoryRestResource(collectionResourceRel = "like", path = "likes")
public interface LikeRepositoryCustom extends MongoRepository<Like, String>,
															CrudRepository<Like, String> {
	
	public Like findByReferenceIdAndUserId(@Param("referenceId") String referenceId, @Param("userId") String userId);
	
	public List<Like> findByReferenceIdAndType(@Param("referenceId") String referenceId, @Param("type") int type);

	public Like findByReferenceIdAndUserIdAndType(@Param("referenceId") String referenceId, 
			@Param("userId") String userId, @Param("type") int type);

}
