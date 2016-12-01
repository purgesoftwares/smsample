package com.allowify.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import com.allowify.model.UploadedFile;

@RepositoryRestResource(collectionResourceRel = "upload-file", path = "upload-files")
public interface UploadedFileRepositoryCustom extends MongoRepository<UploadedFile, String>,
														CrudRepository<UploadedFile, String> {
	
	public List<UploadedFile> findById(@Param("id") String id);
	
	UploadedFile findByFilename(@Param("filename")String filename);

}
