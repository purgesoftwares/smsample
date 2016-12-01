package com.allowify.repository;

/*import java.util.List;*/
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.allowify.model.User;

@RepositoryRestResource(collectionResourceRel = "user", path = "user")
public interface UserRepositoryCustom extends MongoRepository<User, String> {

	public User findByUserName(@Param("userName") String userName);
	
	public User findByEmail(@Param("email") String email);

	public User findEntityByUserName(@Param("userName") String userName);

	public User findEntityByPassword(@Param("userId") String userId);
	
	public User findByReferenceCode(@Param("referenceCode") String referenceCode);

}
