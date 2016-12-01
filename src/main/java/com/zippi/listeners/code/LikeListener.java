package com.zippi.listeners.code;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import com.zippi.events.code.OnCreateLikeEvent;

@Component
public class LikeListener  implements ApplicationListener<OnCreateLikeEvent> {

	@Override
	public void onApplicationEvent(final OnCreateLikeEvent arg0) {
			System.out.println("Listening like");	
	}

}
