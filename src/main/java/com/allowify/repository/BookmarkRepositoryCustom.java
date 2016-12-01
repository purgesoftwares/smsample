/**
 * 
 */
package com.allowify.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.allowify.model.Bookmark;

/**
 * @author shankarp
 *
 */
@RepositoryRestResource(collectionResourceRel = "bookmark", path = "bookmarks")
public interface BookmarkRepositoryCustom extends MongoRepository<Bookmark, String>, CrudRepository<Bookmark,String>  {
	
	
	public Bookmark findByUserId(@Param("userId") String userId);
	
}
