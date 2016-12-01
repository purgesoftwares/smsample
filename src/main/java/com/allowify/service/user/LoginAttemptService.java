package com.allowify.service.user;

import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.allowify.listeners.OnMaxLoginAttemptsEvent;
import com.allowify.model.User;
import com.allowify.repository.UserRepositoryCustom;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;


@Service
public class LoginAttemptService {

	@Autowired
	private Environment env;
	
	@Autowired
	private UserRepositoryCustom ur;
		
    //private LoadingCache<String, Integer> attemptsCache;
    
	@Autowired
    private ApplicationEventPublisher eventPublisher;

    public LoginAttemptService() {
        super();
     /*   attemptsCache = CacheBuilder
        				.newBuilder()
    					.expireAfterWrite( 
    							30,
    							TimeUnit.MINUTES).build(new CacheLoader<String, Integer>() {
            @Override
            public Integer load(final String key) {
                return 0;
            }
        });*/
    }

    public void loginSucceeded(final String key) {
    	//attemptsCache.invalidate(key);
    }
    
    public void removeBlockedCache(final String key) {
    	//attemptsCache.invalidate(key);
    }

    public void loginFailed(final String key) {
    	int attempts = 0;
       /* try {
           // attempts = attemptsCache.get(key);
        } catch (final ExecutionException e) {
            attempts = 0;
        }*/
        attempts++;
        if(attempts == Integer.parseInt(env.getProperty("user.block.max-attempts"))){
	        User user = ur.findByEmail(key);
	       
	        if(user != null){
	        	eventPublisher.publishEvent(new OnMaxLoginAttemptsEvent(user));
	        }
        }
        //attemptsCache.put(key, attempts);
    }

    public boolean isBlocked(final String key) {
    	/*try {
            //return attemptsCache.get(key) >= Integer.parseInt(env.getProperty("user.block.max-attempts"));
        } catch (final ExecutionException e) {
            return false;
        }*/
		return false;
    }

	public void updateLastLogin(String username) {
		User user = ur.findByEmail(username);
		if(user != null){
			user.setLastLogin(new Date());
			if(user.getCreated() == null){
				user.setCreated(new Date());
			}
			ur.save(user);
		}
	}
}
