package com.allowify.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.allowify.model.Comment;



@RepositoryRestResource(collectionResourceRel = "comment", path = "comments")
public interface CommentRepositoryCustom extends MongoRepository<Comment, String>,
													CrudRepository<Comment, String> {

	public Comment findById(@Param("id") String id);
	
	public Comment findByUserId(@Param("userId") String userID);
	
	public Comment findByReferenceId(@Param("referenceId") String referenceId);
	
	public List<Comment> findByParentTypeAndReferenceId(@Param("parentType") String parentType, 
			@Param("referenceId") String referenceId, Pageable pageable);
	
	public Comment findByReferenceIdAndUserId(@Param("referenceId") String referenceId, @Param("userId") String userId);

}
