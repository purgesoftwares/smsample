/**
 * 
 */
package com.allowify.repository;

import java.util.List;

import org.springframework.data.mongodb.core.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.geo.Distance;

import com.allowify.model.Code;
import com.allowify.model.GPlace;

/**
 * @author shankarp
 *
 */
@RepositoryRestResource(collectionResourceRel = "gPlace", path = "places")
public interface GPlaceRepositoryCustom extends MongoRepository<GPlace, String>, CrudRepository<GPlace,String>  {
	
	
	public List<GPlace> findByName(@Param("name") String name);
	
}
