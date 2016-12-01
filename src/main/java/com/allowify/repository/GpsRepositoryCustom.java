package com.allowify.repository;
import java.util.List;




import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.allowify.model.Gps;

@RepositoryRestResource(collectionResourceRel = "gps", path = "gps")
public interface GpsRepositoryCustom extends MongoRepository<Gps, String>, CrudRepository<Gps,String> {
	

	 public List<Gps> findByUserName(@Param("userName") String userName);
	 
	 public Gps findOneByUserName(@Param("userName") String userName);
	 
	 public Gps findOneByUserNameOrderByIdDesc(@Param("userName") String userName);
	 
	 public List<Gps> findByLocationNear(@Param("location") Double[] location);

}
