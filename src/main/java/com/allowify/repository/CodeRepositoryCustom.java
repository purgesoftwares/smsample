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

/**
 * @author shankarp
 *
 */
@RepositoryRestResource(collectionResourceRel = "code", path = "codes")
public interface CodeRepositoryCustom extends MongoRepository<Code, String>, CrudRepository<Code,String>  {
	
	
	public List<Code> findByCode(@Param("code") String code);
	
	public Code findByStatus(@Param("status") int status);
	
	public List<Code> findByUserId(@Param("user-id") String userId);
	
	 //public List<Code> findByLatLongNear(@Param("latLong") double[] latLong, Distance range);

	public List<Code> findByLatLongNear(
			org.springframework.data.geo.Point point, Distance range);

}
