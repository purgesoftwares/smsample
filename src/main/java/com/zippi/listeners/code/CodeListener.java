/**
 * 
 */
package com.zippi.listeners.code;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.zippi.events.code.OnCreateCodeEvent;

/**
 * @author shankarp
 *
 */

@Component
public class CodeListener implements ApplicationListener<OnCreateCodeEvent>{

	
	@Override
    public void onApplicationEvent(final OnCreateCodeEvent event) {
		System.out.println("listening");
    }
	
}
