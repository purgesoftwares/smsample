/**
 * 
 */
package com.allowify.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.allowify.model.Device;

/**
 * @author shankar palsaniya
 *
 */
@RepositoryRestResource(collectionResourceRel = "device", path = "devices")
public interface DeviceRepositoryCustom extends MongoRepository<Device, String>, CrudRepository<Device,String> {


	public List<Device> findByUserName(@Param("userName") String userName);
	
	public Device findByDeviceToken(@Param("deviceToken") String deviceToken);
	 
}
