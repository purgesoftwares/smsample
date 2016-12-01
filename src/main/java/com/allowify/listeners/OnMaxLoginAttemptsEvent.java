package com.allowify.listeners;

import org.springframework.context.ApplicationEvent;

import com.allowify.model.User;


@SuppressWarnings("serial")
public class OnMaxLoginAttemptsEvent extends ApplicationEvent {

    private final User user;
   
    public OnMaxLoginAttemptsEvent(final User user) {
        super(user);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
    
}