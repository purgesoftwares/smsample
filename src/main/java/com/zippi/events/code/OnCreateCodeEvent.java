/**
 * 
 */
package com.zippi.events.code;

import org.springframework.context.ApplicationEvent;

import com.allowify.model.Code;
import com.allowify.model.Comment;
import com.allowify.model.Like;
import com.allowify.model.User;

/**
 * @author shankarp
 *
 */
@SuppressWarnings("serial")
public class OnCreateCodeEvent extends ApplicationEvent {

	 	
	    private final User user;
	    private final Code code;
	  	
	    public OnCreateCodeEvent(final User user, final Code code) {
	        super(code);
	        this.user = user;
	        this.code = code;
	       	       
	    }

	    public User getUser() {
	        return user;
	    }
	    
	    public Code getCode() {
	    	return code;
	    }
	    
	    
}
