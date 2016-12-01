package com.zippi.listeners.code;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import com.zippi.events.code.OnCreateShareEvent;


@Component
public class ShareListener implements ApplicationListener<OnCreateShareEvent> {

	@Override
	public void onApplicationEvent(OnCreateShareEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
