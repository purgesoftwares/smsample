package com.zippi.events.code;

import org.springframework.context.ApplicationEvent;

import com.allowify.model.CodeEvent;
import com.allowify.model.UploadedFile;
import com.allowify.model.User;

@SuppressWarnings("serial")
public class OnUploadPhotoEvent extends ApplicationEvent{

	final UploadedFile uploadedFile;
	
	final CodeEvent codeEvent;
	
	
	public OnUploadPhotoEvent(final UploadedFile uploadedFile, final CodeEvent codeEvent) {
		super(uploadedFile);
		this.uploadedFile = uploadedFile;
		this.codeEvent = codeEvent;
		
	}
	
	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}
	
	public CodeEvent getCodeEvent() {
		return codeEvent;
	}
	
	
}
