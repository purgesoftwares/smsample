package com.allowify.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.allowify.model.CodeEvent;
import com.allowify.model.EventInterested;
import com.allowify.model.User;
import com.allowify.model.UserActivity;
import com.allowify.repository.CodeEventRepositoryCustom;
import com.allowify.repository.EventInterestedRepositoryCustom;
import com.allowify.repository.UserRepositoryCustom;
import com.allowify.service.ActivityService;
import com.zippi.events.code.OnCreateEventInterstedEvent;


@Controller
@RequestMapping("/interest")
public class EventInterestedContoller {
	
	@Autowired
	private EventInterestedRepositoryCustom eventInterestedRepositoryCustom;
	
	@Autowired
    private ApplicationEventPublisher eventPublisher;
	
	@Autowired
    @Qualifier("mongoTemplate")
    public MongoOperations mongoOperations;
	
	@Autowired
	private UserRepositoryCustom userRepositoryCustom;
	
	@Autowired
	private ActivityService service;
	
	@Autowired
	private CodeEventRepositoryCustom codeEventRepositoryCustom;
	

	@RequestMapping(method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ResponseStatus(value = HttpStatus.CREATED)
	@ResponseBody
	public EventInterested create(@RequestBody EventInterested eventInterested) {
		
		eventInterested.setEventId(eventInterested.getEventId());
		eventInterested.setUserId(eventInterested.getUserId());
		
		User user = userRepositoryCustom.findOne(eventInterested.getUserId());
		CodeEvent codeEvent = codeEventRepositoryCustom.findOne(eventInterested.getEventId());
		
		eventInterested =  eventInterestedRepositoryCustom.save(eventInterested);
		
		OnCreateEventInterstedEvent onCreateEventInterstedEvent = new 
				OnCreateEventInterstedEvent(eventInterested, user, codeEvent);
		service.requestUserAcivity(onCreateEventInterstedEvent);
		eventPublisher.publishEvent(onCreateEventInterstedEvent);
		
		return eventInterested;

	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void delete(@PathVariable(value = "id") String id) {
		eventInterestedRepositoryCustom.delete(id);
	
	}
	

}
