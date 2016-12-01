package com.zippi.listeners.code;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.zippi.events.code.OnUploadPhotoEvent;

@Component
public class UploadPhotoListener implements ApplicationListener<OnUploadPhotoEvent>{

	@Override
	public void onApplicationEvent(final OnUploadPhotoEvent arg0) {
			
	}

}
