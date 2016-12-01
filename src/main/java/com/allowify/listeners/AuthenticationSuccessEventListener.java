package com.allowify.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import com.allowify.service.user.LoginAttemptService;


@Component
public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {

    @Autowired
    private LoginAttemptService loginAttemptService;
    
    //@Override
    public void onApplicationEvent(final AuthenticationSuccessEvent e) {
        final WebAuthenticationDetails auth = (WebAuthenticationDetails) e.getAuthentication().getDetails();
        if (auth != null) {
        	loginAttemptService.loginSucceeded(e.getAuthentication().getPrincipal().toString());
        	loginAttemptService.updateLastLogin(e.getAuthentication().getName());
        }
    }

}
