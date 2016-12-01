package com.allowify.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.allowify.model.Code;
import com.allowify.model.CodeEvent;
import com.allowify.model.UploadedFile;
import com.allowify.model.User;
import com.allowify.repository.CodeEventRepositoryCustom;
import com.allowify.repository.CodeRepositoryCustom;
import com.allowify.repository.UploadedFileRepositoryCustom;
import com.allowify.repository.UserRepositoryCustom;
import com.allowify.service.ActivityService;
import com.zippi.events.code.OnUploadPhotoEvent;

@Controller
@RequestMapping("/file")
public class UploadFileController {
	
	@Autowired
	private UploadedFileRepositoryCustom uploadFileRepository;
	
	@Autowired
	private UserRepositoryCustom userRepositoryCustom;
	
	@Autowired
	private CodeRepositoryCustom codeRepositoryCustom;
	
	@Autowired
	private CodeEventRepositoryCustom codeEventRepositoryCustom;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
    private ApplicationEventPublisher eventPublisher;
	
	@Autowired
    @Qualifier("mongoTemplate")
    public MongoOperations mongoOperations;
	
	@Autowired
	private ActivityService activityService ;
	
	/**
     * Size of a byte buffer to read/write file
     */
    private static final int BUFFER_SIZE = 4096;
	
	/**
     * A variable to use in uploaded file names to avoid conflicts in filename
     */
	private static int FILE_COUNTER;
	
	/*
	 * This method is used to upload MultipartFile to the directory
	 * and save uploadFile
	 */
	@RequestMapping(value="/upload", method=RequestMethod.POST, 
			consumes = "multipart/form-data")
    public @ResponseBody UploadedFile uploadFile( 
            @RequestParam("file") MultipartFile file,
            @RequestParam("key") String key,
            @RequestParam("value") String value) throws Exception {
		
		UploadedFile uf = null;
		CodeEvent codeEvent = null;
		//Set<UploadedFile> listOfUploadFiles = new HashSet<UploadedFile>();
		
		uf = new UploadedFile();
		String checkStr = file.getContentType();
				
		String filename = FILE_COUNTER + new Date().getTime() + "-" + file.getOriginalFilename();
		String userDirectoryName = "src/main/resources/uploads/user/";
		String codeDirectoryName = "src/main/resources/uploads/code/";
		String eventDirectoryName = "src/main/resources/uploads/event/";
		BufferedOutputStream stream =null;
		
        if ((!file.isEmpty()) && 
        		((checkStr.equalsIgnoreCase("image/jpeg") || 
        				checkStr.equalsIgnoreCase("image/jpg")
        		|| checkStr.equalsIgnoreCase("image/png") ||  
        				checkStr.equalsIgnoreCase("image/gif")))) {
        	try {
            	   
	                byte[] bytes = file.getBytes();
	                if(key.equalsIgnoreCase("user")){
	                	 stream = new BufferedOutputStream(
	                			 new FileOutputStream(new File(userDirectoryName + filename)));
	                }
	                else if(key.equalsIgnoreCase("code")) {
	                	stream = new BufferedOutputStream(
	                			 new FileOutputStream(new File(codeDirectoryName + filename)));
	                } else if(key.equalsIgnoreCase("event")) {
	                	stream = new BufferedOutputStream(
	                			 new FileOutputStream(new File(eventDirectoryName + filename)));
	                }
	     
	                stream.write(bytes);
	                stream.close();
	               
	                FILE_COUNTER++;
	                
	                uf.setFilename(filename);
	                uf.setLastUpdate(new Date());
	                uploadFileRepository.save(uf);
	                              

	                if(key.equalsIgnoreCase("user")){
	                	User checkUserData = userRepositoryCustom.findByEmail(value);
	                	if(checkUserData.equals(null)) throw new ClassNotFoundException("User not found");
	                	checkUserData.setProfilePicture(uf);
	                	userRepositoryCustom.save(checkUserData);
	                }
	                else if(key.equalsIgnoreCase("code")) {
	                	
	                	List<Code> checkCodeData = codeRepositoryCustom.findByCode(value);
	                	if(checkCodeData.isEmpty()) throw new ClassNotFoundException("Code not found");
	                	checkCodeData.get(0).setUploadFile(uf);
	                	codeRepositoryCustom.save(checkCodeData.get(0));
	                }
	                else if(key.equalsIgnoreCase("event")) {
	                	codeEvent = codeEventRepositoryCustom.findOne(value);
	                	if(codeEvent != null) {
	                		codeEvent.setUploadFile(uf);
	                		codeEventRepositoryCustom.save(codeEvent);
	                		
	                	} else throw new ClassNotFoundException("Event not found");
	              
	                }
	              

            } catch (Exception e) {
            	e.printStackTrace();
            	if(e.getClass()== ClassNotFoundException.class){
            		throw e;
            	}else{
            		throw new Exception("Error in file upload.");
            	}
            }
            
        }

	  		OnUploadPhotoEvent onUploadPhotoEvent = new OnUploadPhotoEvent(uf, codeEvent);
	  		activityService.requestUserAcivity(onUploadPhotoEvent);
         	eventPublisher.publishEvent(onUploadPhotoEvent);
        return uf;
		
    }


	/**
     * Method for handling file download request from client
     */
    @RequestMapping(value = "/{key}/{value}", method = {RequestMethod.GET, RequestMethod.HEAD})
    public void getFile(HttpServletRequest request,
            HttpServletResponse response,
            @PathVariable(value = "key") String key,
            @PathVariable(value = "value") String value) throws IOException {
    	
    	
    	UploadedFile uf = null;
    	String userDirectoryName = "src/main/resources/uploads/user/";
		String codeDirectoryName = "src/main/resources/uploads/code/";
		InputStream inputStream = null;
    	ServletContext context = request.getServletContext();
    	String mimeType =null;
		if(key.equalsIgnoreCase("user")){
			 uf = userRepositoryCustom.findOne(value).getProfilePicture();
			 mimeType = context.getMimeType(userDirectoryName);
			 inputStream = new FileInputStream(userDirectoryName + uf.getFilename());
				
		 }
		 else if(key.equalsIgnoreCase("code")) {
			 uf = codeRepositoryCustom.findByCode(value).get(0).getUploadFile();
			 mimeType = context.getMimeType(codeDirectoryName);
			 inputStream = new FileInputStream(codeDirectoryName + uf.getFilename());
				
		 }
    	       
        if (mimeType == null) {
            // set to binary type if MIME mapping not found
            mimeType = "application/octet-stream";
        }
        
        // set content attributes for the response
        response.setContentType(mimeType);
 
        // set headers for the response
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"",
        		uf.getFilename().substring(uf.getFilename().indexOf("-")+1));
        response.setHeader(headerKey, headerValue);
 
        // get output stream of the response
        OutputStream outStream = response.getOutputStream();
 
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead = -1;
 
        // write bytes read from the input stream into the output stream
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }
 
        inputStream.close();
        outStream.close();
 
    }
 

}
